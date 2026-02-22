import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/l10n/strings_provider.dart';

class RoomCodeDisplay extends ConsumerWidget {
  final String roomCode;

  const RoomCodeDisplay({super.key, required this.roomCode});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final s = ref.watch(stringsProvider);
    return GestureDetector(
      onTap: () {
        Clipboard.setData(ClipboardData(text: roomCode));
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(s.roomCodeCopied),
            duration: const Duration(seconds: 1),
          ),
        );
      },
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            s.room(roomCode),
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              letterSpacing: 2,
            ),
          ),
          const SizedBox(width: 6),
          const Icon(Icons.copy, size: 16),
        ],
      ),
    );
  }
}
