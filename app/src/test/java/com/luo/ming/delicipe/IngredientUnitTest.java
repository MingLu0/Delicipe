package com.luo.ming.delicipe;
import com.luo.ming.delicipe.Helpers.IngredientClassHelper;
import com.luo.ming.delicipe.Models.Ingredient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IngredientUnitTest {

    private Ingredient ingredient;
    private IngredientClassHelper _helper;

    @Before
    public void setUp() {

        ingredient = new Ingredient();
        _helper = new IngredientClassHelper();

    }

    @Test
    public void testUniformUnitsWithCups() {

        String uniformedUnitStr1 = ingredient.uniformUnits("4 cups unbleached high-gluten, bread, or all-purpose flour, chilled");
        String uniformedUnitStr2 = "4 cup unbleached high-gluten, bread, or all-purpose flour, chilled";

        Assert.assertEquals(uniformedUnitStr2,uniformedUnitStr1);
    }

    @Test
    public void testUniformUnitsWithTeaspoon() {

        String uniformedUnitStr1 = ingredient.uniformUnits("1 teaspoon instant yeast");
        String uniformedUnitStr2 = "1 tsp instant yeast";

        Assert.assertEquals(uniformedUnitStr2,uniformedUnitStr1);
    }

    @Test
    public void testUniformUnitsWithSticks() {

        String uniformedUnitStr1 = ingredient.uniformUnits("1 teaspoon instant yeast");
        String uniformedUnitStr2 = "1 tsp instant yeast";

        Assert.assertEquals(uniformedUnitStr2,uniformedUnitStr1);
    }

    @Test
    public void testUniformUnitsWithCapitalLetters() {

        String uniformedUnitStr1 = ingredient.uniformUnits("4 Tablespoons (heaping) Cocoa");
        String uniformedUnitStr2 = "4 tbsp (heaping) cocoa";

        Assert.assertEquals(uniformedUnitStr2,uniformedUnitStr1);
    }



    @Test
    public void testStrip1Parentheses() {

        String uniformedUnitStr1 = ingredient.stripParentheses("1 box red velvet cake mix (I used Duncan Hines)");
        String uniformedUnitStr2 = "1 box red velvet cake mix";

        Assert.assertEquals(uniformedUnitStr2,uniformedUnitStr1);
    }

    @Test
    public void testStrip2Parentheses() {

        String uniformedUnitStr1 = ingredient.stripParentheses("1 box (18.5 Ounce) German Chocolate Cake Mix (I Used Duncan Hines)");
        String uniformedUnitStr2 = "1 box German Chocolate Cake Mix ";

        Assert.assertEquals(uniformedUnitStr2,uniformedUnitStr1);
    }

    @Test
    public void testparseIngredientWithOneCountElementandOneUnit() {

        Ingredient newIngredient = ingredient.parseIngredient("4 cups unbleached high-gluten, bread, or all-purpose flour, chilled");
        Double count = newIngredient.getCount();
        Double expectedCount = Double.valueOf(4);
        String unit = newIngredient.getUnit();
        String ingredient = newIngredient.getIngredient();

        Assert.assertEquals(expectedCount,count);
        Assert.assertEquals("cup",unit);
        Assert.assertEquals("unbleached high gluten, bread, or all purpose flour, chilled ",ingredient);
    }

    @Test
    public void testparseIngredientWithTwoCountElementsandOneUnit() {

        Ingredient newIngredient = ingredient.parseIngredient("1 3/4 (.44 ounce) teaspoons salt");
        Double count = newIngredient.getCount();
        Double expectedCount = 1.75;
        String unit = newIngredient.getUnit();
        String ingredient = newIngredient.getIngredient();

        Assert.assertEquals(expectedCount,count);
        Assert.assertEquals("tsp",unit);
        Assert.assertEquals("salt ",ingredient);
    }

    @Test
    public void testparseIngredientWithThreeCountElementsandOneUnit1() {

        Ingredient newIngredient = ingredient.parseIngredient("1-3/4 stick Butter");
        Double count = newIngredient.getCount();
        Double expectedCount = 1.75;
        String unit = newIngredient.getUnit();
        String ingredient = newIngredient.getIngredient();

        Assert.assertEquals(expectedCount,count);
        Assert.assertEquals("stick",unit);
        Assert.assertEquals("butter ",ingredient);
    }

    @Test
    public void testparseIngredientWithThreeCountElementsandOneUnit2() {

        Ingredient newIngredient = ingredient.parseIngredient("1-1/2 ounce, fluid Red Food Coloring");
        Double count = newIngredient.getCount();
        Double expectedCount = 1.5;
        String unit = newIngredient.getUnit();
        String ingredient = newIngredient.getIngredient();

       // Assert.assertEquals(expectedCount,count);
       // Assert.assertEquals("oz",unit);
        Assert.assertEquals(", fluid Red Food Coloring ",ingredient);
    }

    @Test
    public void testparseIngredientWithZeroCountElementsandOneUnit() {

        Ingredient newIngredient = ingredient.parseIngredient("Semolina flour OR cornmeal for dusting");
        Double count = newIngredient.getCount();
        Double expectedCount = 1.0;
        String unit = newIngredient.getUnit();
        String ingredient = newIngredient.getIngredient();

        Assert.assertEquals(expectedCount,count);
        Assert.assertEquals("",unit);
        Assert.assertEquals("semolina flour or cornmeal for dusting",ingredient);
    }
}
