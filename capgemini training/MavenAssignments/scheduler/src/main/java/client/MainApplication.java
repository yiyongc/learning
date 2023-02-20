package client;

import repository.DayRepoImpl;
import repository.GameRepoImpl;
import repository.IDayRepo;
import repository.IGameRepo;
import repository.IPlayerRepo;
import repository.PlayerRepoImpl;
import service.ISchedulerService;
import service.SchedulerServiceImpl;

public class MainApplication {

	private MainApplication() {}
	
	public static void main(String[] args) {
		//Not implemented yet
		
		
	}
	
	
	@SuppressWarnings("unused")
	private static ISchedulerService setupService() {
		IGameRepo gameRepo = new GameRepoImpl();
		IDayRepo dayRepo = new DayRepoImpl();
		IPlayerRepo playerRepo = new PlayerRepoImpl();
		
		return new SchedulerServiceImpl(gameRepo, playerRepo, dayRepo);
	}
}
