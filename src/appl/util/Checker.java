/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appl.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 *
 * @author ca
 */
public class Checker {

    public static Predicate<Path> isCorrectDirectory = dir -> dir != null && Files.isDirectory(dir);

}
