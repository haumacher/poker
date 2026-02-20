import 'package:flutter_riverpod/flutter_riverpod.dart';

/// Local player's seat index at the table, or -1 if not seated.
final playerSeatProvider = StateProvider<int>((ref) => -1);
