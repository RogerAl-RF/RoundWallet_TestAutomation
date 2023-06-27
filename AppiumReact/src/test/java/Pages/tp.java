package Pages;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.MobileElement;

public class tp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		MobileElement lastElement;
//		//List<String> assetNames=new ArrayList<String>();
//		//String assetLabel=null;
//		
//		while(true)
//		{
//			
//			lastElement=(MobileElement)driver.findElementByXPath("(//android.widget.HorizontalScrollView[@content-desc='List: Watchlist']/android.view.ViewGroup/child::android.view.ViewGroup)[last()]");		
////			List<MobileElement> elementList = driver.findElementsByXPath("//android.widget.HorizontalScrollView[@content-desc='List: Watchlist']/android.view.ViewGroup/child::*");
//			
////			for(MobileElement el:elementList)
////			{	
//			
////				//WAS GIVING STALE ELEMENT AND NOSUCHELEMENT
////				try {
////				
////				assetLabel=el.findElementByXPath("/*/android.widget.TextView").getText();
////				System.out.println("Asset Name: "+assetLabel);
////				assetNames.add(assetLabel);
////				
////				if(!assetNames.contains(assetLabel))
////				assetNames.add(assetLabel);
////				}
////				
////				catch(Exception e)
////				{	lastElement=(MobileElement)driver.findElementByXPath("(//android.widget.HorizontalScrollView[@content-desc='List: Watchlist']/android.view.ViewGroup/child::android.view.ViewGroup)[last()-1]"); // /*/../
////					System.out.println("Ignoring: NoSuchElementException - Element might be half loaded on UI");
////				}
////			}	
//			
//			swipeLeft(id);
//			
//			
//			if(lastElement.equals(driver.findElementByXPath("(//android.widget.HorizontalScrollView[@content-desc='List: Watchlist']/android.view.ViewGroup/child::android.view.ViewGroup)[last()]")))
//				break;
//			
//		}
//		
//		
//		String lastElementId=lastElement.getAttribute("content-desc");
//		System.out.println(lastElementId);
//		int lastElementIndex = Integer.valueOf(lastElementId.replaceAll("[^0-9]", " ").trim());
//		System.out.println(lastElementId+" Index: "+lastElementIndex);
//		//System.out.println(assetNames);
//		
//		driver.findElementByAccessibilityId("Button: Back").click();
//		driver.findElementByXPath("(//android.view.ViewGroup[@content-desc='Button: See All'])[3]").click();
//		
//		int i;
//		List<String> assetNames=new ArrayList<String>();
//		for(i=0;i<=lastElementIndex;i++)
//		{
//			try{
//				assetNames.add(driver.findElementByXPath("//android.view.ViewGroup[@content-desc='Button: Watchlist "+i+"']/android.widget.TextView").getText());
//				System.out.println("Asset Added "+assetNames.get(i));
//			}
//			
//			catch(Exception e) {
//				--i;
//				System.out.println("Ignoring NoSuchElementException! Swiping left");
//				swipeLeft(id);
//			}
//			
//		}	
//		
//		System.out.println(assetNames);
//		
//		
//	}
//	
//	public void swipeDeleteAsset()
//	{
//		String id="List: Watchlist";
//		driver.findElementByXPath("(//android.view.ViewGroup[@content-desc='Button: See All'])[3]").click();
//		
//		while(true){
//			try{
//				driver.findElementByAccessibilityId("test").click();
//				System.out.println("Test Theme Clicked");
//				break;
//			}
//			catch(Exception e)
//			{
//				System.out.println("Finding Element by swiping left");
//				swipeLeft(id);
//			}
//			
//		}
//		
//		
//		
//	}

	}

}
