import 'package:flutter/material.dart';

import 'package:poker_app/l10n/app_strings.dart';
import 'package:poker_app/models/poker_messages.dart';

class ActionBar extends StatefulWidget {
  final GameStateMsg? gameState;
  final int mySeat;
  final AppStrings strings;
  final void Function(ActionType action, int amount) onAction;
  final bool showContinue;
  final VoidCallback? onContinue;

  const ActionBar({
    super.key,
    required this.gameState,
    required this.mySeat,
    required this.strings,
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
    final s = widget.strings;

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
            child: Text(s.continueLabel),
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
          child: SizedBox(
            height: 44,
            child: Center(
              child: Text(
                s.waitingForPlayers,
                style: const TextStyle(color: Colors.white54, fontSize: 14),
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
          if (_showRaiseSlider)
            // Cancel / Confirm only when slider is visible
            Row(
              children: [
                Expanded(
                  child: _actionButton(
                    label: s.cancel,
                    color: Colors.red.shade700,
                    onPressed: () {
                      setState(() {
                        _showRaiseSlider = false;
                        if (_preSelectedAction == ActionType.raise) {
                          _preSelectedAction = null;
                          _preSelectedRaiseAmount = 0;
                        }
                      });
                    },
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: Builder(builder: (_) {
                    final amount = _raiseSliderValue.toInt();
                    final isCall = amount <= _maxBet;
                    return _actionButton(
                      label: isCall ? s.call(_callAmount) : s.confirm(amount),
                      color: isCall ? Colors.blue.shade700 : Colors.green.shade700,
                      onPressed: isPreSelecting
                          ? () {
                              setState(() {
                                if (isCall) {
                                  _preSelectedAction = ActionType.call;
                                  _preSelectedRaiseAmount = 0;
                                } else {
                                  _preSelectedAction = ActionType.raise;
                                  _preSelectedRaiseAmount = amount;
                                }
                                _showRaiseSlider = false;
                              });
                            }
                          : () {
                              if (isCall) {
                                widget.onAction(ActionType.call, 0);
                              } else {
                                widget.onAction(ActionType.raise, amount);
                              }
                              setState(() => _showRaiseSlider = false);
                            },
                    );
                  }),
                ),
              ],
            )
          else
            Row(
              children: [
                // Fold
                Expanded(
                  child: _actionButton(
                    label: s.fold,
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
                          label: s.check,
                          color: Colors.blue.shade700,
                          outlined: isPreSelecting,
                          selected: _preSelectedAction == ActionType.check,
                          onPressed: isPreSelecting
                              ? () => _togglePreSelection(ActionType.check)
                              : () => widget.onAction(ActionType.check, 0),
                        )
                      : _actionButton(
                          label: s.call(_callAmount),
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
                      label: s.raise,
                      color: Colors.green.shade700,
                      outlined: isPreSelecting,
                      selected: _preSelectedAction == ActionType.raise,
                      onPressed: () {
                        if (isPreSelecting && _preSelectedAction == ActionType.raise) {
                          // Deselect raise
                          _togglePreSelection(ActionType.raise);
                        } else {
                          // Show slider
                          setState(() {
                            _raiseSliderValue = _minRaiseTotal.toDouble();
                            _showRaiseSlider = true;
                            if (isPreSelecting) {
                              _preSelectedAction = ActionType.raise;
                              _preSelectedRaiseAmount = _minRaiseTotal;
                            }
                          });
                        }
                      },
                    ),
                  ),
                const SizedBox(width: 8),

                // All-In
                Expanded(
                  child: _actionButton(
                    label: s.allIn,
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

  /// Build the discrete raise steps: min, min+step, min+2*step, ..., then all-in.
  List<int> _buildRaiseSteps() {
    final me = _myPlayerState;
    if (me == null) return [];

    final allIn = me.chips + me.currentBet;
    final min = _minRaiseTotal;
    if (min >= allIn) return [allIn];

    // minRaise is the big blind amount; half-blind = minRaise / 2
    final halfBlind = (_minRaiseIncrement / 2).ceil().clamp(1, allIn);
    final steps = <int>[];
    for (var v = min; v < allIn; v += halfBlind) {
      steps.add(v);
    }
    // Always include all-in as last step
    if (steps.isEmpty || steps.last != allIn) {
      steps.add(allIn);
    }
    return steps;
  }

  Widget _buildRaiseSlider([bool isPreSelecting = false]) {
    final s = widget.strings;
    final steps = _buildRaiseSteps();
    if (steps.length < 2) return const SizedBox.shrink();

    final min = steps.first.toDouble();
    final max = steps.last.toDouble();

    // Snap slider value to nearest step
    int snap(double v) {
      var best = steps.first;
      var bestDist = (v - best).abs();
      for (final st in steps) {
        final d = (v - st).abs();
        if (d < bestDist) {
          bestDist = d;
          best = st;
        }
      }
      return best;
    }

    final sliderAmount = _raiseSliderValue.toInt();
    final isAllIn = sliderAmount == steps.last;
    final isCall = sliderAmount <= _maxBet;
    final label = isCall
        ? s.callSlider(_callAmount)
        : isAllIn
            ? s.allInSlider(steps.last)
            : s.raiseSlider(sliderAmount);

    return Padding(
      padding: const EdgeInsets.only(bottom: 8),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(label, style: const TextStyle(fontWeight: FontWeight.bold)),
              Row(
                children: [
                  _presetButton(s.min, min),
                  _presetButton(s.halfPot, _halfPot(min, max)),
                  _presetButton(s.pot, _potRaise(min, max)),
                ],
              ),
            ],
          ),
          Slider(
            value: _raiseSliderValue.clamp(min, max),
            min: min,
            max: max,
            divisions: steps.length - 1,
            onChanged: (v) => setState(() => _raiseSliderValue = snap(v).toDouble()),
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
