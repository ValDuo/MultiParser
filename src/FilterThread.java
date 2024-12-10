public class FilterThread extends Thread{
    private boolean isActive;
    void disable(){
        isActive= false;
    }
    FilterThread(){
        isActive = true;
    }
    @Override
    public void run() {// Этот метод будет вызван при старте потока
        int i = 0;//счетчик циклов
        while (isActive) { // Вывод в цикле с паузой 0.5 сек
            System.out.println("Поток для фильтрации!" + i++);
            try {
                sleep(500); // Задержка в 0.5 сек
            } catch (Exception e) {
                System.out.println("Поток был прерван!");
            }
        }
    }
}


