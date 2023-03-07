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

package me.blvckbytes.bukkitcommands.config;

import me.blvckbytes.bbconfigmapper.sections.IConfigSection;
import me.blvckbytes.bukkitevaluable.BukkitEvaluable;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class CommandErrorMessagesSection implements IConfigSection {

  private BukkitEvaluable
    malformedDouble,
    malformedFloat,
    malformedLong,
    malformedInteger,
    malformedUuid,
    malformedEnum,
    notAPlayer,
    playerUnknown,
    playerNotOnline,
    missingPermission,
    internalError;

  @Override
  public @Nullable Object defaultFor(Field field) throws Exception {
    if (field.getType() == BukkitEvaluable.class)
      return BukkitEvaluable.of("§cUnconfigured message");
    return IConfigSection.super.defaultFor(field);
  }

  public BukkitEvaluable getMalformedDouble() {
    return malformedDouble;
  }

  public BukkitEvaluable getMalformedFloat() {
    return malformedFloat;
  }

  public BukkitEvaluable getMalformedLong() {
    return malformedLong;
  }

  public BukkitEvaluable getMalformedInteger() {
    return malformedInteger;
  }

  public BukkitEvaluable getMalformedUuid() {
    return malformedUuid;
  }

  public BukkitEvaluable getMalformedEnum() {
    return malformedEnum;
  }

  public BukkitEvaluable getNotAPlayer() {
    return notAPlayer;
  }

  public BukkitEvaluable getPlayerUnknown() {
    return playerUnknown;
  }

  public BukkitEvaluable getPlayerNotOnline() {
    return playerNotOnline;
  }

  public BukkitEvaluable getMissingPermission() {
    return missingPermission;
  }

  public BukkitEvaluable getInternalError() {
    return internalError;
  }
}
