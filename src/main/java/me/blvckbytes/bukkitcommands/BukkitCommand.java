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

import me.blvckbytes.bukkitcommands.error.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public abstract class BukkitCommand extends Command {

  protected static final List<String> EMPTY_STRING_LIST;
  private static final Map<Class<? extends Enum<?>>, EnumInfo> enumerationConstantsCache;

  static {
    EMPTY_STRING_LIST = Collections.unmodifiableList(new ArrayList<>());
    enumerationConstantsCache = new HashMap<>();
  }

  private final ICommandConfigProvider configProvider;

  protected BukkitCommand(ICommandConfigProvider configProvider) {
    super(
      configProvider.getName(),
      configProvider.getDescription(),
      configProvider.getUsage(),
      configProvider.getAliases()
    );

    this.configProvider = configProvider;
  }

  //=========================================================================//
  //                            Abstract Handlers                            //
  //=========================================================================//

  protected abstract void onInvocation(CommandSender sender, String alias, String[] args);

  protected abstract List<String> onTabComplete(CommandSender sender, String alias, String[] args);

  //=========================================================================//
  //                             Bukkit Handlers                             //
  //=========================================================================//

  @Override
  public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
    return executeAndHandleCommandErrors(() -> {
      onInvocation(sender, alias, args);
      return true;
    }, false, sender, alias, args);
  }

  @NotNull
  @Override
  public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
    return executeAndHandleCommandErrors(() -> onTabComplete(sender, alias, args), EMPTY_STRING_LIST, sender, alias, args);
  }

  //=========================================================================//
  //                                Utilities                                //
  //=========================================================================//

  @SuppressWarnings("unchecked")
  protected <T extends Enum<?>> T enumParameter(String[] args, int argumentIndex, Class<T> enumClass) {
    EnumInfo enumInfo = enumerationConstantsCache.computeIfAbsent(enumClass, this::createEnumInfo);
    Object constant = enumInfo.enumConstantByLowerCaseName.get(resolveArgument(args, argumentIndex));

    if (constant == null)
      throw new MalformedEnumError(argumentIndex, enumInfo.originalEnumConstantStrings);

    return (T) constant;
  }

  protected <T extends Enum<?>> T enumParameterOrElse(String[] args, int argumentIndex, Class<T> enumClass, T fallback) {
    return invokeIfArgPresentOrElse(() -> enumParameter(args, argumentIndex, enumClass), fallback);
  }

  protected Player playerParameter(String[] args, int argumentIndex) {
    Player player = Bukkit.getPlayer(resolveArgument(args, argumentIndex));

    if (player == null)
      throw new PlayerNotOnlineError(argumentIndex);

    return player;
  }

  protected Player playerParameterOrElse(String[] args, int argumentIndex, Player fallback) {
    return invokeIfArgPresentOrElse(() -> playerParameter(args, argumentIndex), fallback);
  }

  protected OfflinePlayer offlinePlayerParameter(String[] args, int argumentIndex, boolean hasToHavePlayed) {
    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(resolveArgument(args, argumentIndex));

    if (hasToHavePlayed && !offlinePlayer.hasPlayedBefore())
      throw new PlayerUnknownError(argumentIndex);

    return offlinePlayer;
  }

  protected OfflinePlayer offlinePlayerParameterOrElse(String[] args, int argumentIndex, boolean hasToHavePlayed, OfflinePlayer fallback) {
    return invokeIfArgPresentOrElse(() -> offlinePlayerParameter(args, argumentIndex, hasToHavePlayed), fallback);
  }

  protected UUID uuidParameter(String[] args, int argumentIndex) {
    try {
      return UUID.fromString(resolveArgument(args, argumentIndex));
    } catch (IllegalArgumentException exception) {
      throw new MalformedUuidError(argumentIndex);
    }
  }

  protected UUID uuidParameterOrElse(String[] args, int argumentIndex, UUID fallback) {
    return invokeIfArgPresentOrElse(() -> uuidParameter(args, argumentIndex), fallback);
  }

  protected Integer integerParameter(String[] args, int argumentIndex) {
    try {
      return Integer.parseInt(resolveArgument(args, argumentIndex));
    } catch (NumberFormatException exception) {
      throw new MalformedIntegerError(argumentIndex);
    }
  }

  protected Integer integerParameterOrElse(String[] args, int argumentIndex, Integer fallback) {
    return invokeIfArgPresentOrElse(() -> integerParameter(args, argumentIndex), fallback);
  }

  protected Long longParameter(String[] args, int argumentIndex) {
    try {
      return Long.parseLong(resolveArgument(args, argumentIndex));
    } catch (NumberFormatException exception) {
      throw new MalformedLongError(argumentIndex);
    }
  }

  protected Long longParameterOrElse(String[] args, int argumentIndex, Long fallback) {
    return invokeIfArgPresentOrElse(() -> longParameter(args, argumentIndex), fallback);
  }

  protected Double doubleParameter(String[] args, int argumentIndex) {
    try {
      return Double.parseDouble(resolveArgument(args, argumentIndex));
    } catch (NumberFormatException exception) {
      throw new MalformedDoubleError(argumentIndex);
    }
  }

  protected Double doubleParameterOrElse(String[] args, int argumentIndex, Double fallback) {
    return invokeIfArgPresentOrElse(() -> doubleParameter(args, argumentIndex), fallback);
  }

  protected Float floatParameter(String[] args, int argumentIndex) {
    try {
      return Float.parseFloat(resolveArgument(args, argumentIndex));
    } catch (NumberFormatException exception) {
      throw new MalformedFloatError(argumentIndex);
    }
  }

  protected Float floatParameterOrElse(String[] args, int argumentIndex, Float fallback) {
    return invokeIfArgPresentOrElse(() -> floatParameter(args, argumentIndex), fallback);
  }

  //=========================================================================//
  //                                Internals                                //
  //=========================================================================//

  private EnumInfo createEnumInfo(Class<? extends Enum<?>> enumerationClass) {
    List<String> constants = new ArrayList<>();
    Map<String, Object> lookupTable = new HashMap<>();

    for (Enum<?> constant : enumerationClass.getEnumConstants()) {
      String name = constant.name();
      constants.add(constant.name());
      lookupTable.put(name.toLowerCase(), constant);
    }

    return new EnumInfo(lookupTable, constants);
  }

  private <T> T invokeIfArgPresentOrElse(Supplier<T> callSite, T fallback) {
    try {
      return callSite.get();
    } catch (MissingArgumentError error) {
      return fallback;
    }
  }

  private String resolveArgument(String[] args, int argumentIndex) {
    if (argumentIndex < 0)
      throw new IllegalArgumentException("Argument indices start at zero");

    if (argumentIndex >= args.length)
      throw new MissingArgumentError(argumentIndex);

    return args[argumentIndex];
  }

  private <T> T executeAndHandleCommandErrors(Supplier<T> executable, T returnValueOnError, CommandSender sender, String alias, String[] args) {
    try {
      return executable.get();
    } catch (ACommandError commandError) {
      commandError.handle(configProvider, sender, alias, args);
      return returnValueOnError;
    }
  }
}
