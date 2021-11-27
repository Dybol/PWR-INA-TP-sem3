package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorObjectAdapter implements Door {

	private final ThirdPartyDoor thirdPartyDoor;

	public ThirdPartyDoorObjectAdapter() {
		this.thirdPartyDoor = new ThirdPartyDoor();
	}

	@Override
	public void open(String code) throws IncorrectDoorCodeException, CannotUnlockDoorException {
		try {
			thirdPartyDoor.unlock(code);
			thirdPartyDoor.setState(ThirdPartyDoor.DoorState.OPEN);
		} catch (Throwable t) {
			throw new IncorrectDoorCodeException();
		}
	}

	@Override
	public void close() throws CannotChangeStateOfLockedDoor {
		try {
			thirdPartyDoor.setState(ThirdPartyDoor.DoorState.CLOSED);
		} catch (CannotChangeStateOfLockedDoor e) {
			System.out.println("cannot close doors");
		}
	}

	@Override
	public boolean isOpen() {
		return thirdPartyDoor.getState() == ThirdPartyDoor.DoorState.OPEN;
	}

	@Override
	public void changeCode(String oldCode, String newCode, String newCodeConfirmation) throws IncorrectDoorCodeException, CodeMismatchException, CannotUnlockDoorException, CannotChangeCodeForUnlockedDoor, CannotChangeStateOfLockedDoor {
		open(oldCode);
		if(!newCode.equals(newCodeConfirmation))
			throw new CodeMismatchException();
		thirdPartyDoor.setNewLockCode(newCode);
		close();
	}

	@Override
	public boolean testCode(String code) throws IncorrectDoorCodeException, CannotUnlockDoorException, CannotChangeStateOfLockedDoor {
		open(code);
		if(isOpen()) {
			close();
			return true;
		} else
			return false;
	}
}
