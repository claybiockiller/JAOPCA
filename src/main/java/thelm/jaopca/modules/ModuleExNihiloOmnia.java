package thelm.jaopca.modules;

import thelm.jaopca.api.ModuleBase;

public class ModuleExNihiloOmnia extends ModuleBase {

	/*
	 * Ex Nihilo Omnia has ores for each dimension, so I split this module into four.
	 * Also Ex Nihilo Omnia has the problem of filtering out ores at the wrong time, so we help them.
	 * However we will not help ENO do dimension-only ores, we instead let the users decide which they want.
	 */

	/*public static final BlockProperties GRAVEL_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.SAND).
			setHardnessFunc((entry)->{return 0.6F;}).
			setSoundType(SoundType.GROUND).
			setFallable(true);
	public static final BlockProperties SAND_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.SAND).
			setHardnessFunc((entry)->{return 0.6F;}).
			setSoundType(SoundType.GROUND).
			setFallable(true);
	public static final BlockProperties DUST_PROPERTIES = new BlockProperties().
			setMaterialMapColor(Material.SAND).
			setHardnessFunc((entry)->{return 0.6F;}).
			setSoundType(SoundType.SNOW).
			setFallable(true);

	public static final ItemEntry ORE_CRUSHED_ENTRY = new ItemEntry(EnumEntryType.ITEM, "oreCrushed", new ModelResourceLocation("jaopca:ore_crushed#inventory"));
	public static final ItemEntry ORE_POWDERED_ENTRY = new ItemEntry(EnumEntryType.ITEM, "orePowdered", new ModelResourceLocation("jaopca:ore_powdered#inventory"));
	public static final ItemEntry ORE_SAND_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "oreSand", new ModelResourceLocation("jaopca:ore_sand#normal")).setBlockProperties(SAND_PROPERTIES);
	//oreFine because oreDust may be used
	public static final ItemEntry ORE_DUST_ENTRY = new ItemEntry(EnumEntryType.BLOCK, "oreFine", new ModelResourceLocation("jaopca:ore_fine#normal")).setBlockProperties(DUST_PROPERTIES);

	public static final ArrayList<String> EXISTING_ORES = Lists.<String>newArrayList();

	public static final String ENDER_IO_MESSAGE = "" +
			"<recipeGroup name=\"JAOPCA_ENO\">" +
			"<recipe name=\"%s\" energyCost=\"2000\">" +
			"<input>" +
			"<itemStack oreDictionary=\"%s\" />" +
			"</input>" +
			"<output>" +
			"<itemStack oreDictionary=\"%s\" number=\"5\" />" +
			"<itemStack oreDictionary=\"%s\" number=\"2\" chance=\"0.3\" />" +
			"</output>" +
			"</recipe>" +
			"</recipeGroup>";*/

	@Override
	public String getName() {
		return "exnihiloomnia";
	}

	/*@Override
	public List<ItemEntry> getItemRequests() {
		List<ItemEntry> ret = Lists.<ItemEntry>newArrayList(ORE_CRUSHED_ENTRY, ORE_POWDERED_ENTRY, ORE_SAND_ENTRY, ORE_DUST_ENTRY);
		for(ItemEntry entry : ret) {
			entry.blacklist.addAll(EXISTING_ORES);
		}
		return ret;
	}

	@Override
	public void init() {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("oreCrushed")) {
			if(ENOCompatibility.add_smeltery_melting && Loader.isModLoaded("tconstruct")) {
				ModuleTinkersConstruct.addMeltingRecipe("oreCrushed"+entry.getOreName(), FluidRegistry.getFluid(Utils.to_under_score(entry.getOreName())), 36);
			}
		}

		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("orePowdered")) {
			if(ENOCompatibility.add_smeltery_melting && Loader.isModLoaded("tconstruct")) {
				ModuleTinkersConstruct.addMeltingRecipe("orePowdered"+entry.getOreName(), FluidRegistry.getFluid(Utils.to_under_score(entry.getOreName())), 36);
			}
		}

		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("oreSand")) {
			Utils.addShapelessOreRecipe(Utils.getOreStack("oreSand", entry, 1), new Object[] {
					"oreCrushed"+entry.getOreName(),
					"oreCrushed"+entry.getOreName(),
					"oreCrushed"+entry.getOreName(),
					"oreCrushed"+entry.getOreName(),
			});

			Utils.addSmelting(Utils.getOreStack("oreSand", entry, 1), Utils.getOreStack("ingot", entry, 1), 0);

			addOreHammerRecipe(JAOPCAApi.BLOCKS_TABLE.get("oreSand", entry.getOreName()), Utils.getOreStack("orePowdered", entry, 1));

			if(ENOCompatibility.add_smeltery_melting && Loader.isModLoaded("tconstruct") && FluidRegistry.isFluidRegistered(Utils.to_under_score(entry.getOreName()))) {
				ModuleTinkersConstruct.addMeltingRecipe("oreSand"+entry.getOreName(), FluidRegistry.getFluid(Utils.to_under_score(entry.getOreName())), 144);
			}

			if(ENOCompatibility.aa_crusher && Loader.isModLoaded("actuallyadditions")) {
				addActuallyAdditionsCrusherRecipe(Utils.getOreStack("oreSand", entry, 1), Utils.getOreStack("orePowdered", entry, 5), Utils.getOreStack("orePowdered", entry, 2), 30);
			}

			if(ENOCompatibility.mekanism_crusher && Loader.isModLoaded("Mekanism")) {
				ModuleMekanism.addCrusherRecipe(Utils.getOreStack("oreSand", entry, 1), Utils.getOreStack("orePowdered", entry, 6));
			}

			if(ENOCompatibility.sag_mill && Loader.isModLoaded("EnderIO")) {
				addOreSAGMillRecipe("oreSand"+entry.getOreName(), "orePowdered"+entry.getOreName());
			}
		}

		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("oreFine")) {
			Utils.addShapelessOreRecipe(Utils.getOreStack("oreFine", entry, 1), new Object[] {
					"orePowdered"+entry.getOreName(),
					"orePowdered"+entry.getOreName(),
					"orePowdered"+entry.getOreName(),
					"orePowdered"+entry.getOreName(),
			});

			Utils.addSmelting(Utils.getOreStack("oreFine", entry, 1), Utils.getOreStack("ingot", entry, 1), 0);

			if(ENOCompatibility.add_smeltery_melting && Loader.isModLoaded("tconstruct")) {
				ModuleTinkersConstruct.addMeltingRecipe("oreFine"+entry.getOreName(), FluidRegistry.getFluid(Utils.to_under_score(entry.getOreName())), 144);
			}
		}
	}

	public static void addOreHammerRecipe(Block input, ItemStack output) {
		HammerRegistryEntry hammer = new HammerRegistryEntry(input.getDefaultState(), EnumMetadataBehavior.IGNORED);
		hammer.addReward(output, 100, 0);
		hammer.addReward(output, 100, 0);
		hammer.addReward(output, 100, 0);
		hammer.addReward(output, 100, 0);
		hammer.addReward(output, 50, 2);
		hammer.addReward(output, 5, 1);
		HammerRegistry.add(hammer);
	}

	public static void addOreSieveRecipe(Block input, ItemStack output, int chance) {
		SieveRegistryEntry sieve = new SieveRegistryEntry(input.getDefaultState(), EnumMetadataBehavior.IGNORED);
		sieve.addReward(output, chance);
		SieveRegistry.add(sieve);
	}

	public static void addActuallyAdditionsCrusherRecipe(ItemStack input, ItemStack output, ItemStack output2, int output2chance) {
		//Use reflection because ActuallyAdditions does not need a module and I don't want to create a class for this
		try {
			Class<?> apiClass = Class.forName("de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI");
			Method addMethod = apiClass.getMethod("addCrusherRecipe", ItemStack.class, ItemStack.class, ItemStack.class, Integer.TYPE);
			addMethod.invoke(null, input, output, output2, output2chance);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void addOreSAGMillRecipe(String input, String output) {
		FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", String.format(ENDER_IO_MESSAGE, input, input, output, output));
	}

	static {
		//This should work
		for(String name : OreRegistry.registry.keySet()) {
			EXISTING_ORES.add(StringUtils.capitalize(name));
		};
	}*/
}
