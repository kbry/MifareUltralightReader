import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;

public class NFCReader 
{
	
	private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1',
		(byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6',
		(byte) '7', (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
		(byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F' };
	
	private byte[] m_ByteArray = null;
	private MifareUltralight m_Tag = null;

	public String getTagUID(Tag _tag)
	{
		m_Tag = MifareUltralight.get(_tag);
		try 
		{
			m_Tag.connect();
			m_ByteArray = m_Tag.readPages(0);
			return getHexString(m_ByteArray, m_ByteArray.length);
		} 
		catch (Exception e) 
		{
			Log.e("-NFCReader-", e.toString());
		}
		finally
		{
			if(m_Tag != null)
			{
				try 
				{
					m_Tag.close();
				} catch (Exception ex) 
				{
					Log.e("-NFCReader-", ex.toString());
				}
			}
		}
		return null;	
	}
	
	private static String getHexString(byte[] _raw, int _length) 
	{
		byte[] hex = new byte[2 * _length];
		int index = 0;
		int position = 0;

		for (byte b : _raw) 
		{
			if (position >= _length)
			{
				break;
			}
			position++;
			int temp = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[temp >>> 4];
			hex[index++] = HEX_CHAR_TABLE[temp & 0xF];
		}

		return new String(hex);
	}

}
