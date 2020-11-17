/**
 * Для возможности расширения функционала применены паттерны:
 * для view - композит
 * для Контроллеров - композит
 * для репозиториев - композит
 * создание объектов происходит билдером
 * главное меню - фассад
 */

public class Main {

    public static void main(String[] args) {
        FacadeView facadeView = new FacadeView();
        facadeView.startTheWork();
    }
}
