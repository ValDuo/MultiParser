public class UploadThread extends Thread{
    @Override
    public void run() {// Этот метод будет вызван при старте потока
        int i = 0;//счетчик циклов
        while (!isInterrupted()) { // Вывод в цикле с паузой 0.5 сек
            System.out.println("Поток для загрузки!" + i++);
            try {
                sleep(500); // Задержка в 0.5 сек
            } catch (Exception e) {}
        }
    }
}


