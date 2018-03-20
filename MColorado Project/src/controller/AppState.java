package controller;

public enum AppState {
	INSTANCE;
	
	private boolean modified;
	
	public boolean isModified() {return modified;};
	public void setModified(final boolean state) {modified = state;};
}
