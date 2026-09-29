package com.example.shax_app.Rooms.RoomLogic.GameEngine;


import com.example.shax_app.Rooms.RoomLogic.GameEngine.TheReason;

class ValidationResults{
	private boolean valid;
	private TheReason reason = null;

	public ValidationResults(boolean valid){
		this.valid = valid;
	}

	public ValidationResults(boolean valid, TheReason reason){
		this.valid = valid;
		this.reason = reason;
	}

	public static ValidationResults ok(){
		return new ValidationResults(true);
	}

	public static ValidationResults invalid(TheReason reason){
		return new ValidationResults(false, reason);
	}


	public boolean isValid(){
		return this.valid;
	}

	public TheReason getReason(){
		return this.reason;
	}

}
