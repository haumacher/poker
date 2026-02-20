import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class RoomCodeDisplay extends StatelessWidget {
  final String roomCode;

  const RoomCodeDisplay({super.key, required this.roomCode});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Clipboard.setData(ClipboardData(text: roomCode));
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Room code copied'),
            duration: Duration(seconds: 1),
          ),
        );
      },
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            'Room: $roomCode',
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
