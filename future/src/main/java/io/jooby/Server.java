package io.jooby;

import javax.annotation.Nonnull;

public interface Server {

  @Nonnull Server mode(@Nonnull Mode mode);

  int port();

  @Nonnull Server port(int port);

  @Nonnull Server start(@Nonnull Router router);

  @Nonnull Server stop();
}
