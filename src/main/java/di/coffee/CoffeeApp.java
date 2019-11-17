package di.coffee;

import dagger.Component;

import javax.inject.Singleton;

public class CoffeeApp {
    @Singleton
    @Component(modules = {DripCoffeeModule.class})
    public interface CoffeeShop {
        CoffeeMaker maker();
    }

    public static void init() {
        CoffeeShop coffeeShop = DaggerCoffeeApp_CoffeeShop.builder().build();
        CoffeeMaker maker = coffeeShop.maker();
        maker.brew();
    }
}