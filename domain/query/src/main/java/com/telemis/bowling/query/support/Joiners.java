package com.telemis.bowling.query.support;

import com.google.common.base.Joiner;

public class Joiners {

	private Joiners() {
	}

	public static Joiner COMMA = Joiner.on(", ").skipNulls();
	public static Joiner SPACE = Joiner.on(" ").useForNull(" ");
}
