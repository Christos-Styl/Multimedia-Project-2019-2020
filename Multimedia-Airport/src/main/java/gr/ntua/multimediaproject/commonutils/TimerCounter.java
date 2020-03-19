package gr.ntua.multimediaproject.commonutils;

import gr.ntua.multimediaproject.airport.AirportSingleton;

public class TimerCounter implements Runnable {
    private AirportSingleton airport;

    public TimerCounter(AirportSingleton airport){
        this.airport = airport;
    }

    public AirportSingleton getAirport() {
        return airport;
    }

    public void setAirport(AirportSingleton airport) {
        this.airport = airport;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(AbstractHelper.getRealMillisecondsForOneSimulationMinute());
                airport.incrementTimeElapsedInMinutes();
            }
        }
        catch (InterruptedException ex){
            System.out.println(airport.getTimeElapsedInMinutes() + " - INFO: TimerCounter interrupted... Thread will now stop.");
        }
    }
}
