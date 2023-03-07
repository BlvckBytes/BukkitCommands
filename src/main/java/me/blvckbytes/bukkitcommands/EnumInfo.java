/*
 * MIT License
 *
 * Copyright (c) 2023 BlvckBytes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.blvckbytes.bukkitcommands;

import java.util.*;

public class EnumInfo implements IEnumInfo {

  private final Class<? extends Enum<?>> enumClass;
  private final List<String> enumConstantNames;
  private final List<Enum<?>> enumConstants;

  public final Map<String, Enum<?>> enumConstantByLowerCaseName;

  public EnumInfo(Class<? extends Enum<?>> enumClass) {
    this.enumClass = enumClass;
    this.enumConstants = Collections.unmodifiableList(Arrays.asList(enumClass.getEnumConstants()));

    List<String> names = new ArrayList<>();
    Map<String, Enum<?>> table = new HashMap<>();

    for (Enum<?> constant : this.enumConstants) {
      String name = constant.name();
      names.add(name);
      table.put(name.toLowerCase(), constant);
    }

    this.enumConstantNames = Collections.unmodifiableList(names);
    this.enumConstantByLowerCaseName = Collections.unmodifiableMap(table);
  }

  @Override
  public Class<? extends Enum<?>> getEnumClass() {
    return enumClass;
  }

  @Override
  public List<Enum<?>> getEnumConstants() {
    return enumConstants;
  }

  @Override
  public List<String> getEnumConstantNames() {
    return enumConstantNames;
  }
}
