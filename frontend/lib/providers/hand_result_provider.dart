import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'package:poker_app/models/poker_messages.dart';

final handResultProvider = StateProvider<HandResultMsg?>((ref) => null);
