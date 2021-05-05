package vn.zeus.web.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	private Keys id;
	private String content;

	@Override
	public String toString() {
		return content;
	}

	public enum Keys {
		I0001, I0002, I0003, I0004, I0005, I0006, I0007, I0008, I0009, I0010, I0011, I0012, I0013, I0014, I0015, I0016,
		I0017, I0018, I0019, I0020, I0021, I0022, I0023, I0024, I0025, I0026, I0027, I0028, I0029, I0030, E0001, E0002,
		E0003, E0004, E0005, E0006, E0007, E0008, E0009, E0010, E0011, E0012, E0013, E0014, E0015, E0016, E0017, E0018,
		E0019, E0020, E0021, E0022, E0023, E0024, E0025, E0026, E0027, E0028, E0029, E0030, E0031, E0032, E0033, E0034,
		E0035, E0036, E0037, E0038, E0039, E0040, E0041, E0042, E0043, E0044, E0045, E0046, E0047, E0048, E0049, E0050,
		S0001, S0002, S0003, S0004, S0005, S0006, S0007, S0008, S0009, S0010, S0011, S0012
	}
}