package de.haumacher.games.poker.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Tracks bets and calculates main pot and side pots.
 *
 * <p>Each betting round, players contribute bets. When players are all-in with unequal stacks,
 * side pots are created so each player can only win from players who matched their contribution.</p>
 */
public class Pot {

	/** Total amount each seat has contributed across all rounds. */
	private final Map<Integer, Long> contributions = new HashMap<>();

	/** Seats that are still eligible for pots (haven't folded). */
	private final Set<Integer> eligibleSeats = new TreeSet<>();

	public void addBet(int seat, long amount) {
		contributions.merge(seat, amount, Long::sum);
		eligibleSeats.add(seat);
	}

	public void fold(int seat) {
		eligibleSeats.remove(seat);
		// contributions remain — folded player's money stays in the pot
	}

	public long getTotal() {
		return contributions.values().stream().mapToLong(Long::longValue).sum();
	}

	/**
	 * Calculate side pots based on unequal all-in contributions.
	 *
	 * @return ordered list of pots from main pot (smallest contribution level) outward.
	 *         Each pot has an amount and the set of eligible seats that can win it.
	 */
	public List<PotInfo> calculateSidePots() {
		if (contributions.isEmpty()) {
			return List.of();
		}

		// Get sorted unique contribution levels
		TreeSet<Long> levels = new TreeSet<>(contributions.values());

		List<PotInfo> pots = new ArrayList<>();
		long previousLevel = 0;

		for (long level : levels) {
			long perPlayer = level - previousLevel;
			if (perPlayer <= 0) continue;

			// Count how many players contributed at least this level
			long potAmount = 0;
			List<Integer> potEligible = new ArrayList<>();

			for (var entry : contributions.entrySet()) {
				if (entry.getValue() >= level) {
					potAmount += perPlayer;
				}
				// But also add partial contributions from players between previous and current level
				// Actually — everyone who contributed >= previousLevel contributes (level - previousLevel) or their remaining
			}

			// Recalculate properly
			potAmount = 0;
			for (var entry : contributions.entrySet()) {
				long contributed = entry.getValue();
				long contributionToThisPot = Math.min(contributed, level) - Math.min(contributed, previousLevel);
				potAmount += contributionToThisPot;
			}

			// Eligible = players who contributed at least this level AND haven't folded
			for (int seat : eligibleSeats) {
				if (contributions.getOrDefault(seat, 0L) >= level) {
					potEligible.add(seat);
				}
			}

			if (potAmount > 0) {
				pots.add(new PotInfo(potAmount, potEligible));
			}

			previousLevel = level;
		}

		return pots;
	}

	/**
	 * Reset the pot for a new hand.
	 */
	public void reset() {
		contributions.clear();
		eligibleSeats.clear();
	}

	public record PotInfo(long amount, List<Integer> eligibleSeats) {}
}
