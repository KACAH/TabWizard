package gp5;

/**
 * The class GP5Channel is a dummy representation of Guitar Pro 5 channel. 
 * It is needed because channels in guitar pro have many settings which aren't needed in TabsWizard 
 * 
 * @author Deniss Paltovs (KACAH)
 */
class GP5Channel {
	public short instrument = 0;
	public short volume = 0;
	public short balance = 0;
	public short chorus = 0;
	public short reverb = 0;
	public short phaser = 0;
	public short tremolo = 0;
}
