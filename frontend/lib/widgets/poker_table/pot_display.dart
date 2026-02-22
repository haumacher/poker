import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/l10n/strings_provider.dart';
import 'package:poker_app/models/poker_messages.dart';

class PotDisplay extends ConsumerWidget {
  final int pot;
  final List<SidePot> sidePots;

  const PotDisplay({super.key, required this.pot, required this.sidePots});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    if (pot == 0) return const SizedBox.shrink();

    final s = ref.watch(stringsProvider);

    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
          decoration: BoxDecoration(
            color: Colors.black54,
            borderRadius: BorderRadius.circular(12),
          ),
          child: Text(
            s.potDisplay(pot),
            style: const TextStyle(
              color: Colors.amber,
              fontWeight: FontWeight.bold,
              fontSize: 14,
            ),
          ),
        ),
        if (sidePots.isNotEmpty)
          Padding(
            padding: const EdgeInsets.only(top: 2),
            child: Text(
              sidePots.map((sp) => s.sideDisplay(sp.amount)).join(' | '),
              style: const TextStyle(color: Colors.amber, fontSize: 10),
            ),
          ),
      ],
    );
  }
}
