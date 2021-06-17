package ru.ijo42.grinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class GrinderTileEntity extends TileEntity implements ITickable {

    public static final int SIZE = 2;

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            GrinderTileEntity.this.markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot == 0) {
                return !processRecipe(stack).isEmpty();
            }
            return false;
        }
    };

    public static ItemStack processRecipe(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
            return new ItemStack(Blocks.COBBLESTONE, 8);
        } else if (item == Item.getItemFromBlock(Blocks.GRAVEL)) {
            return new ItemStack(Blocks.SAND, 8);
        } else if (item == Item.getItemFromBlock(Blocks.COBBLESTONE)) {
            return new ItemStack(Blocks.GRAVEL, 2);
        } else if (item == Item.getItemFromBlock(Blocks.DIRT) && itemStack.getCount() > 1) {
            return new ItemStack(Blocks.CLAY);
        } else if (item == Item.getItemFromBlock(Blocks.STONE)) {
            return new ItemStack(Blocks.COBBLESTONE);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.world.getTileEntity(this.pos) == this && !isInvalid() && playerIn
                .getDistanceSq(
                        (double) this.pos.getX() + 0.5D,
                        (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <=
                64.0D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
        return super.getCapability(capability, facing);
    }

    public void update() {
        if (!this.world.isRemote && this.canGrind()) {
            this.smeltItem();
        }
    }

    private boolean canGrind() {
        final ItemStack outputStack = this.itemStackHandler.getStackInSlot(1);
        final ItemStack inputStack = this.itemStackHandler.getStackInSlot(0);
        final ItemStack processable = processRecipe(inputStack);
        if (!(inputStack.isEmpty() || processable.isEmpty())) {
            if (outputStack.isEmpty()) {
                return true;
            } else if (!outputStack.isItemEqual(processable)) {
                return false;
            } else if (outputStack.getCount() + processable.getCount() <= this.itemStackHandler.getSlotLimit(0) &&
                    outputStack.getCount() + processable.getCount() <= outputStack.getMaxStackSize()) {
                return true;
            } else {
                return outputStack.getCount() + processable.getCount() <= processable.getMaxStackSize();
            }
        } else {
            return false;
        }
    }

    public void smeltItem() {
        if (this.canGrind()) {
            ItemStack inputStack = this.itemStackHandler.getStackInSlot(0);
            ItemStack outputStack = this.itemStackHandler.getStackInSlot(1);
            ItemStack result = processRecipe(inputStack);

            if (outputStack.isEmpty()) {
                this.itemStackHandler.setStackInSlot(1, result.copy());
            } else if (outputStack.isItemEqual(result)) {
                outputStack.grow(result.getCount());
            }

            inputStack.shrink(inputStack.getItem() == Item.getItemFromBlock(Blocks.DIRT) ? 2 : 1);
        }
    }

}