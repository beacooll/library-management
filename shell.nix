{
  pkgs ? import <nixpkgs> { },
}:

pkgs.mkShell {
  name = "library-management-dev";

  buildInputs = with pkgs; [
    jdk17
    maven
    mysql80
  ];

  shellHook = ''
    # Пути для локального MySQL
    export MYSQL_DATADIR="$PWD/mysql-data"
    export MYSQL_SOCKET="$PWD/mysql.sock"
    export MYSQL_PIDFILE="$PWD/mysql.pid"

    # Создаём директорию данных
    mkdir -p "$MYSQL_DATADIR"

    # Инициализация только при первом запуске
    if [ ! -d "$MYSQL_DATADIR/mysql" ]; then
      echo "Инициализация MySQL (первый запуск)..."
      mysqld --initialize-insecure \
        --datadir="$MYSQL_DATADIR" \
        --user=$(whoami)
    fi

    # Запуск MySQL, если не запущен
    if [ ! -f "$MYSQL_PIDFILE" ] || ! kill -0 $(cat "$MYSQL_PIDFILE") 2>/dev/null; then
      echo "Запуск MySQL сервера..."
      mysqld \
        --datadir="$MYSQL_DATADIR" \
        --socket="$MYSQL_SOCKET" \
        --pid-file="$MYSQL_PIDFILE" \
        --port=3306 \
        --user=$(whoami) &
      
      MYSQL_PID=$!
      echo $MYSQL_PID > "$MYSQL_PIDFILE"

      # Ждём запуска
      sleep 8
    fi

    # Создаём базу, если её нет
    if ! mysql --socket="$MYSQL_SOCKET" -u root -e "USE library_db" 2>/dev/null; then
      echo "Создание базы данных library_db..."
      mysql --socket="$MYSQL_SOCKET" -u root \
        -e "CREATE DATABASE IF NOT EXISTS library_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
    fi

    echo "MySQL готов! База: library_db"
    echo "Подключение: mysql -u root --socket=$MYSQL_SOCKET"
    echo "Остановка: kill \$(cat $MYSQL_PIDFILE) && rm -f $MYSQL_PIDFILE $MYSQL_SOCKET"
    echo "Запуск приложения: mvn spring-boot:run"
  '';

}
