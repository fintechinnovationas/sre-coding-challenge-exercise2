package io.neonomics.codingchallenge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class HeyHo {
	private String band;
	private String song;
	private String reply;

	public HeyHo(String band, String song, String reply) {
		this.band = band;
		this.song = song;
		this.reply = reply;
	}

}
