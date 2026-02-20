import 'package:flutter/material.dart';

class CardBack extends StatelessWidget {
  final double width;
  final double height;

  const CardBack({super.key, this.width = 44, this.height = 62});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width,
      height: height,
      decoration: BoxDecoration(
        color: const Color(0xFF1565C0),
        borderRadius: BorderRadius.circular(4),
        border: Border.all(color: Colors.grey.shade600),
        boxShadow: const [BoxShadow(color: Colors.black26, blurRadius: 2, offset: Offset(1, 1))],
      ),
      child: Center(
        child: Container(
          width: width - 8,
          height: height - 8,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(2),
            border: Border.all(color: Colors.white24),
          ),
        ),
      ),
    );
  }
}
