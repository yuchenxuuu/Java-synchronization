package cs131.pa3.CarsTunnels;

import cs131.pa3.Abstract.Direction;
import cs131.pa3.Abstract.Tunnel;
import cs131.pa3.Abstract.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The class for the Basic Tunnel, extending Tunnel.
 * @author Yuchen Xu
 * @Date 03/18/2020
 */
public class BasicTunnel extends Tunnel{

	/**
	 * Creates a new instance of a basic tunnel with the given name
	 * @param name the name of the basic tunnel
	 */
	private int carCount;//the count of the car inside the tunnel
	private int sledCount;//the count of sled inside the tunnel
	private Direction dir;//the direction of the vehicle
	private List<Vehicle> vlist;//vlist is used to record what kind of vehicle is in the tunnel

	/**
	 * the constructor for the basic tunnel
	 * @param name
	 */
	public BasicTunnel(String name) {
		super(name);
		carCount = 0;
		sledCount = 0;
		vlist = new ArrayList<>();
	}

	/**
	 *  The method that enabled the vehicle to enter the tunnel considering racing conditions
	 * @param  vehicle The vehicle that is attempting to enter
	 * @return if the vehicle can enter the tunnel return true, else return false
	 */
	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle) {
		if(vlist.size() == 3){//if the tunnel is already full
			return false;
		}
		if(vlist.size() == 0 && vehicle != null){//if the tunnel is empty
			this.dir = vehicle.getDirection();
			if(vehicle instanceof Car){
				carCount ++;
			}else if(vehicle instanceof Sled){
				sledCount ++;
			}
			vlist.add(vehicle);
			return true;
		}else{//if there exist some vehicles on the tunnel
			if(vehicle instanceof Car){
				if(this.dir.equals(vehicle.getDirection()) && (carCount < 3 && sledCount == 0)){//requirement for the entry of the acr
					carCount ++;
					vlist.add(vehicle);
					return true;
				}else{
					return false;
				}
			}else if(vehicle instanceof Sled){
				return false;
			}
		}

		return false;
	}

	/**
	 * The method that enabled the vehicle to exit the tunnel considering racing conditions
	 * @param vehicle The vehicle that is exiting the tunnel
	 */
	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle) {
		if(vehicle instanceof Car){
			carCount --;
		}else{
			sledCount --;
			this.dir = null;
		}

		for(Vehicle v: vlist){//remove the vehicle from the list
			if(v.equals(vehicle)){
				vlist.remove(v);
				break;
			}
		}
	}
	
}
