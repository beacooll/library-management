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
    export MYSQL_DATADIR=$PWD/mysql-data
    export MYSQL_SOCKET=$PWD/mysql.sock
    export MYSQL_PIDFILE=$PWD/mysql.pid

    if [ ! -d "$MYSQL_DATADIR/mysql" ]; then
      echo "Инициализация MySQL (первый запуск)..."
      rm -rf $MYSQL_DATADIR
      mkdir -p $MYSQL_DATADIR

      mysqld --initialize-insecure \
        --datadir=$MYSQL_DATADIR \
        --basedir=${pkgs.mysql80}
    fi

    if [ ! -f "$MYSQL_PIDFILE" ]; then
      echo "Запуск MySQL..."
      mysqld \
        --datadir=$MYSQL_DATADIR \
        --socket=$MYSQL_SOCKET \
        --pid-file=$MYSQL_PIDFILE \
        --port=3306 &
      sleep 5
    fi

    mysql --socket=$MYSQL_SOCKET -u root \
      -e "CREATE DATABASE IF NOT EXISTS library_db CHARACTER SET utf8mb4;" || true

    echo "Готово"
    echo "mvn spring-boot:run"
  '';

}
