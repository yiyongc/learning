package com.yiyongc.javagrpclearn.sample;

import com.google.protobuf.Timestamp;
import com.yiyongc.javagrpclearn.pb.*;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class Generator {

  public static void main(String[] args) {
    Generator generator = new Generator();
    Laptop laptop = generator.newLaptop();
    System.out.println(laptop);
  }

  private final Random rand;

  public Generator() {
    rand = new Random();
  }

  public Keyboard newKeyboard() {
    return Keyboard.newBuilder()
        .setLayout(randomKeyboardLayout())
        .setBacklit(rand.nextBoolean())
        .build();
  }

  public CPU newCPU() {
    String brand = randomCPUBrand();
    String name = randomCPUName(brand);

    int numCores = randomInt(2, 8);
    int numThreads = randomInt(numCores, 12);

    double minGhz = randomDouble(2.0, 3.5);
    double maxGhz = randomDouble(minGhz, 5.0);

    return CPU.newBuilder()
        .setBrand(brand)
        .setName(name)
        .setNumberCores(numCores)
        .setNumberThreads(numThreads)
        .setMinGhz(minGhz)
        .setMaxGhz(maxGhz)
        .build();
  }

  public GPU newGPU() {
    String brand = randomGPUBrand();
    String name = randomGPUName(brand);

    double minGhz = randomDouble(1.0, 1.5);
    double maxGhz = randomDouble(minGhz, 2.0);

    Memory memory =
        Memory.newBuilder().setValue(randomInt(2, 6)).setUnit(Memory.Unit.GIGABYTE).build();

    return GPU.newBuilder()
        .setBrand(brand)
        .setName(name)
        .setMemory(memory)
        .setMinGhz(minGhz)
        .setMaxGhz(maxGhz)
        .build();
  }

  public Memory newRAM() {
    return Memory.newBuilder().setValue(randomInt(4, 64)).setUnit(Memory.Unit.GIGABYTE).build();
  }

  public Storage newSSD() {
    Memory memory =
        Memory.newBuilder().setValue(randomInt(128, 1024)).setUnit(Memory.Unit.GIGABYTE).build();
    return Storage.newBuilder().setMemory(memory).setDriver(Storage.Driver.SSD).build();
  }

  public Storage newHDD() {
    Memory memory =
        Memory.newBuilder().setValue(randomInt(1, 6)).setUnit(Memory.Unit.TERABYTE).build();
    return Storage.newBuilder().setMemory(memory).setDriver(Storage.Driver.HDD).build();
  }

  public Screen newScreen() {
    int height = randomInt(1080, 4320);
    int width = height * 16 / 9;

    Screen.Resolution resolution =
        Screen.Resolution.newBuilder().setHeight(height).setWidth(width).build();

    return Screen.newBuilder()
        .setSizeInch(randomFloat(13, 17))
        .setResolution(resolution)
        .setPanel(randomScreenPanel())
        .setMultitouch(rand.nextBoolean())
        .build();
  }

  public Laptop newLaptop() {
    String brand = randomLaptopBrand();
    String name = randomLaptopName(brand);

    double weightKg = randomDouble(1.0, 3.0);
    double priceUsd = randomDouble(1500, 3500);

    int releaseYear = randomInt(2015, 2019);

    return Laptop.newBuilder()
        .setBrand(brand)
        .setName(name)
        .setCpu(newCPU())
        .setRam(newRAM())
        .addGpus(newGPU())
        .addStorages(newSSD())
        .addStorages(newHDD())
        .setScreen(newScreen())
        .setKeyboard(newKeyboard())
        .setWeightKg(weightKg)
        .setPriceUsd(priceUsd)
        .setUpdatedAt(timestampNow())
        .setReleaseYear(releaseYear)
        .setId(UUID.randomUUID().toString())
        .build();
  }

  private Timestamp timestampNow() {
    Instant now = Instant.now();
    return Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build();
  }

  private Screen.Panel randomScreenPanel() {
    return rand.nextBoolean() ? Screen.Panel.IPS : Screen.Panel.OLED;
  }

  private float randomFloat(float min, float max) {
    return min + rand.nextFloat() * (max - min);
  }

  private String randomCPUBrand() {
    return randomStringFromSet("Intel", "AMD");
  }

  private String randomCPUName(String brand) {
    if (brand.equals("Intel")) {
      return randomStringFromSet(
          "Xeon E-2286M", "Core i9-9980HK", "Core i7-9750H", "Core i5-9400F", "Core i3-1005G1");
    }
    return randomStringFromSet("Ryzen 7 PRO 2700U", "Ryzen 5 PRO 3500U", "Ryzen 3 PRO 3200GE");
  }

  private String randomGPUBrand() {
    return randomStringFromSet("NVIDIA", "AMD");
  }

  private String randomGPUName(String brand) {
    if (brand.equals("NVIDIA")) {
      return randomStringFromSet("RTX 2060", "RTX 2070", "GTX 1660-Ti", "GTX 1070");
    }
    return randomStringFromSet("RX 590", "RX 580", "RX 5700-XT", "RX Vega-56");
  }

  private String randomStringFromSet(String... args) {
    return args[rand.nextInt(args.length)];
  }

  private Keyboard.Layout randomKeyboardLayout() {
    return switch (rand.nextInt(3)) {
      case 1 -> Keyboard.Layout.QWERTY;
      case 2 -> Keyboard.Layout.QWERTZ;
      default -> Keyboard.Layout.AZERTY;
    };
  }

  private String randomLaptopBrand() {
    return randomStringFromSet("Acer", "Dell", "Lenovo");
  }

  private String randomLaptopName(String brand) {
    return switch (brand) {
      case "Acer" -> randomStringFromSet("Aspire", "Predator", "Nitro");
      case "Dell" -> randomStringFromSet("Latitude", "Vostro", "XPS", "Alienware");
      default -> randomStringFromSet("Thinkpad X1", "Thinkpad P1", "Thinkpad P53");
    };
  }

  private int randomInt(int min, int max) {
    return min + rand.nextInt(max - min + 1);
  }

  private double randomDouble(double min, double max) {
    return min + rand.nextDouble() * (max - min);
  }
}
