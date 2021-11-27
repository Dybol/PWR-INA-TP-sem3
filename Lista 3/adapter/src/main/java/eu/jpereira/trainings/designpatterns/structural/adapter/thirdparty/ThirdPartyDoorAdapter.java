package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorAdapter extends ThirdPartyDoor implements Door {

	@Override
	public void open(String code) throws IncorrectDoorCodeException, CannotUnlockDoorException {
		try {
			super.unlock(code);
			super.setState(DoorState.OPEN);
		} catch (Throwable t) {
			throw new IncorrectDoorCodeException();
		}
	}

	@Override
	public void close() {
		try {
			super.setState(DoorState.CLOSED);
		} catch (CannotChangeStateOfLockedDoor e) {
			System.out.println("cannot close doors");
		}
	}

	@Override
	public boolean isOpen() {
		return super.getState() == DoorState.OPEN;
	}

	@Override
	public void changeCode(String oldCode, String newCode, String newCodeConfirmation) throws IncorrectDoorCodeException, CodeMismatchException, CannotUnlockDoorException, CannotChangeCodeForUnlockedDoor {
		open(oldCode);
		if(!newCode.equals(newCodeConfirmation))
			throw new CodeMismatchException();
		super.setNewLockCode(newCode);
		close();
	}

	@Override
	public boolean testCode(String code) throws IncorrectDoorCodeException, CannotUnlockDoorException {
		open(code);

		if(isOpen()) {
			close();
			return true;
		} else
			return false;
	}
}
