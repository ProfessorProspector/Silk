package io.github.prospector.silk.fluid;

import io.github.prospector.silk.util.TagSerializable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidInstance implements TagSerializable {
	public static final String FLUID_KEY = "Fluid";
	public static final String AMOUNT_KEY = "Amount";
	public static final String TAG_KEY = "Tag";

	public static final FluidInstance EMPTY = new FluidInstance(Fluids.EMPTY, 0);

	protected Fluid fluid;
	protected int amount;
	protected CompoundTag tag;

	public FluidInstance(Fluid fluid, int amount) {
		this.fluid = fluid;
		this.amount = amount;
	}

	public FluidInstance(Fluid fluid) {
		this(fluid, 0);
	}

	public FluidInstance() {
		this(Fluids.EMPTY);
	}

	public FluidInstance(CompoundTag tag) {
		this();
		fromTag(tag);
	}

	public Fluid getFluid() {
		return fluid;
	}

	public int getAmount() {
		return amount;
	}

	public CompoundTag getTag() {
		return tag;
	}

	public FluidInstance setFluid(Fluid fluid) {
		this.fluid = fluid;
		return this;
	}

	public FluidInstance setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	public FluidInstance subtractAmount(int amount) {
		this.amount -= amount;
		return this;
	}

	public FluidInstance addAmount(int amount) {
		this.amount += amount;
		return this;
	}

	public void setTag(CompoundTag tag) {
		this.tag = tag;
	}

	public boolean isEmpty() {
		return this.getFluid() == Fluids.EMPTY || this.getAmount() == 0;
	}

	public FluidInstance copy() {
		return new FluidInstance().setFluid(fluid).setAmount(amount);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		tag.putString(FLUID_KEY, Registry.FLUID.getId(fluid).toString());
		tag.putInt(AMOUNT_KEY, amount);
		if (this.tag != null && !this.tag.isEmpty()) {
			tag.put(TAG_KEY, this.tag);
	    	}
		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		fluid = Registry.FLUID.get(new Identifier(tag.getString(FLUID_KEY)));
		amount = tag.getInt(AMOUNT_KEY);
		if (tag.containsKey(TAG_KEY)) {
			this.tag = tag.getCompound(TAG_KEY);
		}
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof FluidInstance && fluid == ((FluidInstance) obj).getFluid() && amount == ((FluidInstance) obj).getAmount();
	}
}
