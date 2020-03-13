package net.devtech.industrialcrust.blocks.power;

public interface EnergyHolder {
	void setPower(int power);
	int getPower();
	int getMaxPower();
	int getMaxCurrent();
}
