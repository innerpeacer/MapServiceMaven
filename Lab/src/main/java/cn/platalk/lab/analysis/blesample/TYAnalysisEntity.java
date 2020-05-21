package cn.platalk.lab.analysis.blesample;

public class TYAnalysisEntity {
	public enum EntityType {
		GPS("GPS"), BLE("BLE");

		final String name;

		EntityType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private final EntityType type;
	private final double error;
	private final double accuracy;

	public TYAnalysisEntity(EntityType type) {
		this(type, 0, 0);
	}

	public TYAnalysisEntity(EntityType type, double error, double accuracy) {
		this.type = type;
		this.error = error;
		this.accuracy = accuracy;
	}

	public EntityType getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("%s: %f -> %f", type.getName(), accuracy, error);
	}
}
