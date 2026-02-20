import 'package:flutter/material.dart';

import 'package:poker_app/models/poker_messages.dart';

class ActionBar extends StatefulWidget {
  final GameStateMsg? gameState;
  final int mySeat;
  final void Function(ActionType action, int amount) onAction;

  const ActionBar({
    super.key,
    required this.gameState,
    required this.mySeat,
    required this.onAction,
  });

  @override
  State<ActionBar> createState() => _ActionBarState();
}

class _ActionBarState extends State<ActionBar> {
  double _raiseSliderValue = 0;
  bool _showRaiseSlider = false;

  bool get _isMyTurn =>
      widget.gameState != null && widget.gameState!.currentPlayerSeat == widget.mySeat && widget.mySeat >= 0;

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

  int get _minRaise => widget.gameState?.minRaise ?? 0;

  int get _myChips => _myPlayerState?.chips ?? 0;

  @override
  Widget build(BuildContext context) {
    if (!_isMyTurn) {
      return const SizedBox(height: 60);
    }

    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
      decoration: const BoxDecoration(
        color: Color(0xFF1A1A1A),
        border: Border(top: BorderSide(color: Colors.white12)),
      ),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          if (_showRaiseSlider) _buildRaiseSlider(),
          Row(
            children: [
              // Fold
              Expanded(
                child: _actionButton(
                  label: 'Fold',
                  color: Colors.red.shade700,
                  onPressed: () => widget.onAction(ActionType.fold, 0),
                ),
              ),
              const SizedBox(width: 8),

              // Check or Call
              Expanded(
                child: _canCheck
                    ? _actionButton(
                        label: 'Check',
                        color: Colors.blue.shade700,
                        onPressed: () => widget.onAction(ActionType.check, 0),
                      )
                    : _actionButton(
                        label: 'Call $_callAmount',
                        color: Colors.blue.shade700,
                        onPressed: () => widget.onAction(ActionType.call, 0),
                      ),
              ),
              const SizedBox(width: 8),

              // Raise
              if (_myChips > _callAmount)
                Expanded(
                  child: _actionButton(
                    label: _showRaiseSlider ? 'Confirm' : 'Raise',
                    color: Colors.green.shade700,
                    onPressed: () {
                      if (_showRaiseSlider) {
                        widget.onAction(ActionType.raise, _raiseSliderValue.toInt());
                        setState(() => _showRaiseSlider = false);
                      } else {
                        setState(() {
                          _raiseSliderValue = _minRaise.toDouble();
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
                  onPressed: () => widget.onAction(ActionType.allIn, 0),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildRaiseSlider() {
    final me = _myPlayerState;
    if (me == null) return const SizedBox.shrink();

    final maxRaise = (me.chips + me.currentBet).toDouble();
    final min = _minRaise.toDouble();
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
    required VoidCallback onPressed,
  }) {
    return SizedBox(
      height: 44,
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          backgroundColor: color,
          foregroundColor: Colors.white,
          padding: const EdgeInsets.symmetric(horizontal: 4),
          minimumSize: Size.zero,
        ),
        onPressed: onPressed,
        child: FittedBox(child: Text(label)),
      ),
    );
  }
}
