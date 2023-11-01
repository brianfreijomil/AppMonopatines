- averiguar manejo del Timer

- EJEMPLO DE TIMER

- public class TimerExample {

  public static void main(String[] args) {
  // Crea una instancia de Timer
  Timer timer = new Timer();

        // Programa una tarea que se ejecutará después de 15 minutos (900,000 milisegundos)
        timer.schedule(new MyTask(), 900000);
  }

  // Clase que extiende TimerTask y define la tarea a ejecutar
  static class MyTask extends TimerTask {
  @Override
  public void run() {
  System.out.println("¡Tarea ejecutada después de 15 minutos!");
  // Agrega aquí la lógica que deseas ejecutar cuando se complete el temporizador.
  }
  }
  }

