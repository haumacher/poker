import 'dart:math' as math;

import 'package:flutter/material.dart';

import 'package:poker_app/models/poker_messages.dart';
import 'package:poker_app/theme/app_theme.dart';
import 'package:poker_app/widgets/poker_table/community_cards.dart';
import 'package:poker_app/widgets/poker_table/pot_display.dart';
import 'package:poker_app/widgets/poker_table/seat_widget.dart';

class PokerTable extends StatelessWidget {
  final GameStateMsg? gameState;
  final int mySeat;

  const PokerTable({super.key, required this.gameState, required this.mySeat});

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        final w = constraints.maxWidth;
        final h = constraints.maxHeight;

        return Stack(
          children: [
            // Green felt oval
            Center(
              child: Container(
                width: w * 0.85,
                height: h * 0.65,
                decoration: BoxDecoration(
                  color: AppTheme.feltGreen,
                  borderRadius: BorderRadius.circular(h * 0.325),
                  border: Border.all(color: const Color(0xFF5D4037), width: 6),
                  boxShadow: const [BoxShadow(color: Colors.black45, blurRadius: 12)],
                ),
              ),
            ),

            // Community cards + pot in center
            Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  PotDisplay(
                    pot: gameState?.pot ?? 0,
                    sidePots: gameState?.sidePots ?? [],
                  ),
                  const SizedBox(height: 8),
                  CommunityCards(cards: gameState?.communityCards ?? []),
                ],
              ),
            ),

            // 9 seats positioned around the oval
            ..._buildSeats(w, h),
          ],
        );
      },
    );
  }

  List<Widget> _buildSeats(double w, double h) {
    // 9 seats around an oval. Seat positions as angles (in radians).
    // Local player is at bottom center. We rotate the layout so mySeat
    // appears at the bottom.
    //
    // Seat layout (angle from top, clockwise):
    //   0: bottom center (270° = -90°)
    //   1: bottom-right
    //   2: right
    //   3: top-right
    //   4: top center
    //   5: top-left
    //   6: left
    //   7: bottom-left
    //   8: bottom center-left (just left of seat 0)
    //
    // Angles for 9 seats evenly spaced, starting from bottom:
    const baseAngles = <double>[
      270, // seat 0 - bottom center
      310, // seat 1
      350, // seat 2
       20, // seat 3
       60, // seat 4
      100, // seat 5
      140, // seat 6
      180, // seat 7
      220, // seat 8
    ];

    // Rotation offset so that mySeat appears at position 0 (bottom center)
    final rotationOffset = mySeat >= 0 ? mySeat : 0;

    final cx = w / 2;
    final cy = h / 2;
    // Cap radii so seat widgets (120×~80) stay within bounds
    final rx = math.min(w * 0.45, cx - 68);  // 60 half-width + 8 margin
    final ry = math.min(h * 0.45, cy - 48);  // 40 half-height + 8 margin

    final widgets = <Widget>[];

    for (var visualIndex = 0; visualIndex < 9; visualIndex++) {
      // Which actual seat index maps to this visual position
      final seatIndex = (visualIndex + rotationOffset) % 9;
      final angle = baseAngles[visualIndex] * math.pi / 180;

      final x = cx + rx * math.cos(angle) - 60; // 60 = half seat widget width
      final y = cy + ry * math.sin(angle) - 40; // 40 ≈ half seat widget height

      final player = _playerAtSeat(seatIndex);

      widgets.add(
        Positioned(
          left: x,
          top: y,
          child: SeatWidget(
            player: player,
            seatIndex: seatIndex,
            isDealer: gameState?.dealerSeat == seatIndex,
            isCurrentTurn: gameState?.currentPlayerSeat == seatIndex,
            isLocalPlayer: seatIndex == mySeat,
          ),
        ),
      );
    }

    return widgets;
  }

  PlayerState? _playerAtSeat(int seat) {
    if (gameState == null) return null;
    for (final p in gameState!.players) {
      if (p.seat == seat) return p;
    }
    return null;
  }
}
