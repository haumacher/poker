import 'package:flutter/material.dart';

import 'package:poker_app/models/poker_messages.dart';

class ActionBar extends StatefulWidget {
  final GameStateMsg? gameState;
  final int mySeat;
  final void Function(ActionType action, int amount) onAction;
  final bool showContinue;
  final VoidCallback? onContinue;

  const ActionBar({
    super.key,
    required this.gameState,
    required this.mySeat,
    required this.onAction,
    this.showContinue = false,
    this.onContinue,
  });

  @override
  State<ActionBar> createState() => _ActionBarState();
}

class _ActionBarState extends State<ActionBar> {
  double _raiseSliderValue = 0;
  bool _showRaiseSlider = false;
  ActionType? _preSelectedAction;
  int _preSelectedRaiseAmount = 0;

  bool get _isMyTurn =>
      widget.gameState != null && widget.gameState!.currentPlayerSeat == widget.mySeat && widget.mySeat >= 0;

  bool get _isActiveInHand {
    final me = _myPlayerState;
    if (me == null) return false;
    final phase = widget.gameState?.phase;
    if (phase == null || phase == Phase.waitingForPlayers || phase == Phase.showdown) return false;
    return me.status == PlayerStatus.active;
  }

  PlayerState? get _myPlayerState {
    if (widget.gameState == null) return null;
    for (final p in widget.gameState!.players) {
      if (p.seat == widget.mySeat) return p;
    }
    return null;
  }

  int get _maxBet {
    if (widget.gameState == null) return 0;
    var max = 0;
    for (final p in widget.gameState!.players) {
      if (p.currentBet > max) max = p.currentBet;
    }
    return max;
  }

  bool get _canCheck {
    final me = _myPlayerState;
    if (me == null) return false;
    return me.currentBet >= _maxBet;
  }

  int get _callAmount {
    final me = _myPlayerState;
    if (me == null) return 0;
    return _maxBet - me.currentBet;
  }

  /// The server sends the minimum raise increment (e.g. big blind size).
  int get _minRaiseIncrement => widget.gameState?.minRaise ?? 0;

  /// Minimum total bet for a legal raise = current max bet + min increment.
  int get _minRaiseTotal => _maxBet + _minRaiseIncrement;

  int get _myChips => _myPlayerState?.chips ?? 0;

  /// Whether the player can afford the full call amount.
  bool get _canAffordCall => _myChips >= _callAmount;

  /// Whether the player can make a legal raise (has enough to meet min raise).
  bool get _canRaise {
    final me = _myPlayerState;
    if (me == null) return false;
    // Must have chips beyond the call, and total bet must reach minRaiseTotal
    return _myChips > _callAmount && (me.chips + me.currentBet) >= _minRaiseTotal;
  }

  @override
  void didUpdateWidget(ActionBar oldWidget) {
    super.didUpdateWidget(oldWidget);

    final wasMyTurn = oldWidget.gameState != null &&
        oldWidget.gameState!.currentPlayerSeat == oldWidget.mySeat &&
        oldWidget.mySeat >= 0;

    // Turn just arrived with a pre-selection → validate and commit
    if (_isMyTurn && !wasMyTurn && _preSelectedAction != null) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        _tryCommitPreSelection();
      });
    }

    // Game state changed while waiting → revalidate
    if (!_isMyTurn && _preSelectedAction != null) {
      if (!_isPreSelectionValid(_preSelectedAction!)) {
        setState(() {
          _preSelectedAction = null;
          _preSelectedRaiseAmount = 0;
          _showRaiseSlider = false;
        });
      }
    }
  }

  bool _isPreSelectionValid(ActionType action) {
    return switch (action) {
      ActionType.fold || ActionType.allIn => true,
      ActionType.check => _canCheck,
      ActionType.call => !_canCheck && _canAffordCall,
      ActionType.raise => _canRaise && _preSelectedRaiseAmount >= _minRaiseTotal,
    };
  }

  void _tryCommitPreSelection() {
    if (!_isMyTurn || _preSelectedAction == null) return;
    final action = _preSelectedAction!;
    final amount = _preSelectedRaiseAmount;

    // Clear pre-selection before committing to avoid double-fire
    setState(() {
      _preSelectedAction = null;
      _preSelectedRaiseAmount = 0;
      _showRaiseSlider = false;
    });

    if (_isPreSelectionValid(action)) {
      widget.onAction(action, action == ActionType.raise ? amount : 0);
    }
  }

  void _togglePreSelection(ActionType action, [int raiseAmount = 0]) {
    setState(() {
      if (_preSelectedAction == action) {
        _preSelectedAction = null;
        _preSelectedRaiseAmount = 0;
        _showRaiseSlider = false;
      } else {
        _preSelectedAction = action;
        _preSelectedRaiseAmount = raiseAmount;
        if (action != ActionType.raise) _showRaiseSlider = false;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    if (widget.showContinue) {
      return Container(
        padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
        decoration: const BoxDecoration(
          color: Color(0xFF1A1A1A),
          border: Border(top: BorderSide(color: Colors.white12)),
        ),
        child: SizedBox(
          width: double.infinity,
          height: 44,
          child: ElevatedButton(
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.blue.shade700,
              foregroundColor: Colors.white,
            ),
            onPressed: widget.onContinue,
            child: const Text('Continue'),
          ),
        ),
      );
    }

    if (!_isMyTurn && !_isActiveInHand) {
      final phase = widget.gameState?.phase;
      if (phase == Phase.waitingForPlayers && widget.mySeat >= 0) {
        return Container(
          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
          decoration: const BoxDecoration(
            color: Color(0xFF1A1A1A),
            border: Border(top: BorderSide(color: Colors.white12)),
          ),
          child: const SizedBox(
            height: 44,
            child: Center(
              child: Text(
                'Waiting for other players...',
                style: TextStyle(color: Colors.white54, fontSize: 14),
              ),
            ),
          ),
        );
      }
      return const SizedBox(height: 60);
    }

    final isPreSelecting = !_isMyTurn;

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
      decoration: const BoxDecoration(
        color: Color(0xFF1A1A1A),
        border: Border(top: BorderSide(color: Colors.white12)),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          if (_isMyTurn && widget.gameState!.turnTimeRemaining > 0)
            Padding(
              padding: const EdgeInsets.only(bottom: 4),
              child: Text(
                '${widget.gameState!.turnTimeRemaining}s',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: widget.gameState!.turnTimeRemaining <= 10 ? Colors.red : Colors.orange,
                ),
              ),
            ),
          if (_showRaiseSlider) _buildRaiseSlider(isPreSelecting),
          Row(
            children: [
              // Fold
              Expanded(
                child: _actionButton(
                  label: 'Fold',
                  color: Colors.red.shade700,
                  outlined: isPreSelecting,
                  selected: _preSelectedAction == ActionType.fold,
                  onPressed: isPreSelecting
                      ? () => _togglePreSelection(ActionType.fold)
                      : () => widget.onAction(ActionType.fold, 0),
                ),
              ),
              const SizedBox(width: 8),

              // Check or Call
              Expanded(
                child: _canCheck
                    ? _actionButton(
                        label: 'Check',
                        color: Colors.blue.shade700,
                        outlined: isPreSelecting,
                        selected: _preSelectedAction == ActionType.check,
                        onPressed: isPreSelecting
                            ? () => _togglePreSelection(ActionType.check)
                            : () => widget.onAction(ActionType.check, 0),
                      )
                    : _actionButton(
                        label: 'Call $_callAmount',
                        color: Colors.blue.shade700,
                        outlined: isPreSelecting,
                        selected: _preSelectedAction == ActionType.call,
                        onPressed: _canAffordCall
                            ? (isPreSelecting
                                ? () => _togglePreSelection(ActionType.call)
                                : () => widget.onAction(ActionType.call, 0))
                            : null,
                      ),
              ),
              const SizedBox(width: 8),

              // Raise
              if (_canRaise)
                Expanded(
                  child: _actionButton(
                    label: _showRaiseSlider ? 'Confirm' : 'Raise',
                    color: Colors.green.shade700,
                    outlined: isPreSelecting,
                    selected: _preSelectedAction == ActionType.raise,
                    onPressed: isPreSelecting
                        ? () {
                            if (_showRaiseSlider && _preSelectedAction == ActionType.raise) {
                              // Confirm the raise amount
                              setState(() {
                                _preSelectedRaiseAmount = _raiseSliderValue.toInt();
                                _showRaiseSlider = false;
                              });
                            } else if (_preSelectedAction == ActionType.raise) {
                              // Deselect raise
                              _togglePreSelection(ActionType.raise);
                            } else {
                              // Select raise and show slider
                              setState(() {
                                _raiseSliderValue = _minRaiseTotal.toDouble();
                                _showRaiseSlider = true;
                                _preSelectedAction = ActionType.raise;
                                _preSelectedRaiseAmount = _minRaiseTotal;
                              });
                            }
                          }
                        : () {
                            if (_showRaiseSlider) {
                              widget.onAction(ActionType.raise, _raiseSliderValue.toInt());
                              setState(() => _showRaiseSlider = false);
                            } else {
                              setState(() {
                                _raiseSliderValue = _minRaiseTotal.toDouble();
                                _showRaiseSlider = true;
                              });
                            }
                          },
                  ),
                ),
              const SizedBox(width: 8),

              // All-In
              Expanded(
                child: _actionButton(
                  label: 'All In',
                  color: Colors.orange.shade800,
                  outlined: isPreSelecting,
                  selected: _preSelectedAction == ActionType.allIn,
                  onPressed: isPreSelecting
                      ? () => _togglePreSelection(ActionType.allIn)
                      : () => widget.onAction(ActionType.allIn, 0),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildRaiseSlider([bool isPreSelecting = false]) {
    final me = _myPlayerState;
    if (me == null) return const SizedBox.shrink();

    final maxRaise = (me.chips + me.currentBet).toDouble();
    final min = _minRaiseTotal.toDouble();
    if (min >= maxRaise) return const SizedBox.shrink();

    return Padding(
      padding: const EdgeInsets.only(bottom: 8),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text('Raise: ${_raiseSliderValue.toInt()}',
                  style: const TextStyle(fontWeight: FontWeight.bold)),
              Row(
                children: [
                  _presetButton('Min', min),
                  _presetButton('1/2 Pot', _halfPot(min, maxRaise)),
                  _presetButton('Pot', _potRaise(min, maxRaise)),
                ],
              ),
            ],
          ),
          Slider(
            value: _raiseSliderValue.clamp(min, maxRaise),
            min: min,
            max: maxRaise,
            divisions: (maxRaise - min).toInt().clamp(1, 100),
            onChanged: (v) => setState(() => _raiseSliderValue = v),
          ),
        ],
      ),
    );
  }

  double _halfPot(double min, double max) {
    final half = ((widget.gameState?.pot ?? 0) / 2 + _maxBet).toDouble();
    return half.clamp(min, max);
  }

  double _potRaise(double min, double max) {
    final pot = ((widget.gameState?.pot ?? 0) + _maxBet).toDouble();
    return pot.clamp(min, max);
  }

  Widget _presetButton(String label, double value) {
    return Padding(
      padding: const EdgeInsets.only(left: 4),
      child: SizedBox(
        height: 28,
        child: TextButton(
          style: TextButton.styleFrom(
            padding: const EdgeInsets.symmetric(horizontal: 8),
            minimumSize: Size.zero,
          ),
          onPressed: () => setState(() => _raiseSliderValue = value),
          child: Text(label, style: const TextStyle(fontSize: 11)),
        ),
      ),
    );
  }

  Widget _actionButton({
    required String label,
    required Color color,
    required VoidCallback? onPressed,
    bool outlined = false,
    bool selected = false,
  }) {
    final isOutlined = outlined && !selected;

    return SizedBox(
      height: 44,
      child: isOutlined
          ? OutlinedButton(
              style: OutlinedButton.styleFrom(
                foregroundColor: color,
                side: BorderSide(color: color),
                disabledForegroundColor: color.withValues(alpha: 0.3),
                padding: const EdgeInsets.symmetric(horizontal: 4),
                minimumSize: Size.zero,
              ),
              onPressed: onPressed,
              child: FittedBox(child: Text(label)),
            )
          : ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: color,
                foregroundColor: Colors.white,
                disabledBackgroundColor: color.withValues(alpha: 0.3),
                disabledForegroundColor: Colors.white38,
                padding: const EdgeInsets.symmetric(horizontal: 4),
                minimumSize: Size.zero,
              ),
              onPressed: onPressed,
              child: FittedBox(child: Text(label)),
            ),
    );
  }
}
