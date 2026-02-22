import 'package:poker_app/utils/hand_evaluator.dart';

enum AppLocale { en, de }

class AppStrings {
  // App
  final String appTitle;

  // Home Screen
  final String server;
  final String displayName;
  final String displayNameRequired;
  final String createTable;
  final String joinTable;
  final String turnTimer;
  final String noLimit;
  final String roomCode;
  final String roomCodeHint;
  final String connected;
  final String roomCodeInvalid;
  final String serverTimeout;
  final String Function(String error) connectionFailed;

  // Table Screen
  final String table;
  final String reconnecting;
  final String Function(String name, int amount, String hand) playerWins;

  // Action Bar
  final String fold;
  final String check;
  final String raise;
  final String allIn;
  final String cancel;
  final String continueLabel;
  final String waitingForPlayers;
  final String min;
  final String halfPot;
  final String pot;
  final String Function(int amount) call;
  final String Function(int amount) confirm;
  final String Function(int amount) callSlider;
  final String Function(int amount) allInSlider;
  final String Function(int amount) raiseSlider;

  // Seat Widget
  final String dealer;
  final String folded;
  final String allInStatus;
  final String Function(int n) seat;

  // Room Code Display
  final String roomCodeCopied;
  final String Function(String code) room;

  // Pot Display
  final String Function(int amount) potDisplay;
  final String Function(int amount) sideDisplay;

  // Hand Ranks
  final String noCards;
  final String highCard;
  final String pair;
  final String twoPair;
  final String threeOfAKind;
  final String straight;
  final String flush;
  final String fullHouse;
  final String fourOfAKind;
  final String straightFlush;
  final String royalFlush;

  const AppStrings({
    required this.appTitle,
    required this.server,
    required this.displayName,
    required this.displayNameRequired,
    required this.createTable,
    required this.joinTable,
    required this.turnTimer,
    required this.noLimit,
    required this.roomCode,
    required this.roomCodeHint,
    required this.connected,
    required this.roomCodeInvalid,
    required this.serverTimeout,
    required this.connectionFailed,
    required this.table,
    required this.reconnecting,
    required this.playerWins,
    required this.fold,
    required this.check,
    required this.raise,
    required this.allIn,
    required this.cancel,
    required this.continueLabel,
    required this.waitingForPlayers,
    required this.min,
    required this.halfPot,
    required this.pot,
    required this.call,
    required this.confirm,
    required this.callSlider,
    required this.allInSlider,
    required this.raiseSlider,
    required this.dealer,
    required this.folded,
    required this.allInStatus,
    required this.seat,
    required this.roomCodeCopied,
    required this.room,
    required this.potDisplay,
    required this.sideDisplay,
    required this.noCards,
    required this.highCard,
    required this.pair,
    required this.twoPair,
    required this.threeOfAKind,
    required this.straight,
    required this.flush,
    required this.fullHouse,
    required this.fourOfAKind,
    required this.straightFlush,
    required this.royalFlush,
  });
}

// --- English ---

String _enConnectionFailed(String e) => 'Connection failed: $e';
String _enPlayerWins(String name, int amount, String hand) =>
    '$name wins $amount ($hand)';
String _enCall(int amount) => 'Call $amount';
String _enConfirm(int amount) => 'Confirm $amount';
String _enCallSlider(int amount) => 'Call: $amount';
String _enAllInSlider(int amount) => 'All In: $amount';
String _enRaiseSlider(int amount) => 'Raise: $amount';
String _enSeat(int n) => 'Seat $n';
String _enRoom(String code) => 'Room: $code';
String _enPotDisplay(int amount) => 'Pot: $amount';
String _enSideDisplay(int amount) => 'Side: $amount';

const en = AppStrings(
  appTitle: 'Poker',
  server: 'Server',
  displayName: 'Display Name *',
  displayNameRequired: 'Display name is required',
  createTable: 'Create Table',
  joinTable: 'Join Table',
  turnTimer: 'Turn Timer',
  noLimit: 'No Limit',
  roomCode: 'Room Code',
  roomCodeHint: 'e.g. ABC123',
  connected: 'Connected',
  roomCodeInvalid: 'Room code must be 6 characters',
  serverTimeout: 'Server did not respond in time',
  connectionFailed: _enConnectionFailed,
  table: 'Table',
  reconnecting: 'Reconnecting...',
  playerWins: _enPlayerWins,
  fold: 'Fold',
  check: 'Check',
  raise: 'Raise',
  allIn: 'All In',
  cancel: 'Cancel',
  continueLabel: 'Continue',
  waitingForPlayers: 'Waiting for other players...',
  min: 'Min',
  halfPot: '1/2 Pot',
  pot: 'Pot',
  call: _enCall,
  confirm: _enConfirm,
  callSlider: _enCallSlider,
  allInSlider: _enAllInSlider,
  raiseSlider: _enRaiseSlider,
  dealer: 'D',
  folded: 'FOLDED',
  allInStatus: 'ALL IN',
  seat: _enSeat,
  roomCodeCopied: 'Room code copied',
  room: _enRoom,
  potDisplay: _enPotDisplay,
  sideDisplay: _enSideDisplay,
  noCards: 'No Cards',
  highCard: 'High Card',
  pair: 'Pair',
  twoPair: 'Two Pair',
  threeOfAKind: 'Three of a Kind',
  straight: 'Straight',
  flush: 'Flush',
  fullHouse: 'Full House',
  fourOfAKind: 'Four of a Kind',
  straightFlush: 'Straight Flush',
  royalFlush: 'Royal Flush',
);

// --- German ---

String _deConnectionFailed(String e) => 'Verbindung fehlgeschlagen: $e';
String _dePlayerWins(String name, int amount, String hand) =>
    '$name gewinnt $amount ($hand)';
String _deCall(int amount) => 'Mitgehen $amount';
String _deConfirm(int amount) => 'Bestätigen $amount';
String _deCallSlider(int amount) => 'Mitgehen: $amount';
String _deAllInSlider(int amount) => 'All In: $amount';
String _deRaiseSlider(int amount) => 'Erhöhen: $amount';
String _deSeat(int n) => 'Platz $n';
String _deRoom(String code) => 'Raum: $code';
String _dePotDisplay(int amount) => 'Pot: $amount';
String _deSideDisplay(int amount) => 'Side: $amount';

const de = AppStrings(
  appTitle: 'Poker',
  server: 'Server',
  displayName: 'Anzeigename *',
  displayNameRequired: 'Anzeigename ist erforderlich',
  createTable: 'Tisch erstellen',
  joinTable: 'Tisch beitreten',
  turnTimer: 'Zugzeit',
  noLimit: 'Kein Limit',
  roomCode: 'Raumcode',
  roomCodeHint: 'z.B. ABC123',
  connected: 'Verbunden',
  roomCodeInvalid: 'Raumcode muss 6 Zeichen lang sein',
  serverTimeout: 'Server hat nicht rechtzeitig geantwortet',
  connectionFailed: _deConnectionFailed,
  table: 'Tisch',
  reconnecting: 'Verbindung wird wiederhergestellt...',
  playerWins: _dePlayerWins,
  fold: 'Fold',
  check: 'Check',
  raise: 'Raise',
  allIn: 'All In',
  cancel: 'Abbrechen',
  continueLabel: 'Weiter',
  waitingForPlayers: 'Warte auf andere Spieler...',
  min: 'Min',
  halfPot: '1/2 Pot',
  pot: 'Pot',
  call: _deCall,
  confirm: _deConfirm,
  callSlider: _deCallSlider,
  allInSlider: _deAllInSlider,
  raiseSlider: _deRaiseSlider,
  dealer: 'D',
  folded: 'GEPASST',
  allInStatus: 'ALL IN',
  seat: _deSeat,
  roomCodeCopied: 'Raumcode kopiert',
  room: _deRoom,
  potDisplay: _dePotDisplay,
  sideDisplay: _deSideDisplay,
  noCards: 'Keine Karten',
  highCard: 'Höchste Karte',
  pair: 'Paar',
  twoPair: 'Zwei Paare',
  threeOfAKind: 'Drilling',
  straight: 'Straße',
  flush: 'Flush',
  fullHouse: 'Full House',
  fourOfAKind: 'Vierling',
  straightFlush: 'Straight Flush',
  royalFlush: 'Royal Flush',
);

// --- Lookup ---

const strings = {
  AppLocale.en: en,
  AppLocale.de: de,
};

/// Map a [HandRank] to its localized display name.
String handRankName(AppStrings s, HandRank rank) => switch (rank) {
      HandRank.highCard => s.highCard,
      HandRank.onePair => s.pair,
      HandRank.twoPair => s.twoPair,
      HandRank.threeOfAKind => s.threeOfAKind,
      HandRank.straight => s.straight,
      HandRank.flush => s.flush,
      HandRank.fullHouse => s.fullHouse,
      HandRank.fourOfAKind => s.fourOfAKind,
      HandRank.straightFlush => s.straightFlush,
      HandRank.royalFlush => s.royalFlush,
    };
