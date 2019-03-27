package com.luo.ming.delicipe;

import com.luo.ming.delicipe.Models.Recipe;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeUnitTest {

    private Recipe recipe;

    /**
     * Set up the environment for testing
     */
    @Before
    public void setUp() {
        recipe = new Recipe();
    }

    @Test
    public void convertIngredientStringIntoArray() {


        assertEquals(4, 2 + 2);
    }

}
