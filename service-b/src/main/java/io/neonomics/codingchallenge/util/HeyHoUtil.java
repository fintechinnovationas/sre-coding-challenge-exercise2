package io.neonomics.codingchallenge.util;

import io.neonomics.codingchallenge.model.HeyHo;

import java.util.List;

public final class HeyHoUtil {

	private HeyHoUtil() {
	}

	/**
	 * 
	 * Method to help us get dummy data
	 * 
	 * @return list of candidates
	 */
	public static List<HeyHo> getHeyHo() {
		var heyHo = List.of(
				new HeyHo("Ramones", "Blitzkrieg Bop", "lets go! \uD83E\uDD18"));

		return heyHo;
	}
}
