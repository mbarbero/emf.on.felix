package org.example;

import java.io.IOException;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.examples.extlibrary.EXTLibraryFactory;
import org.eclipse.emf.examples.extlibrary.EXTLibraryPackage;
import org.eclipse.emf.examples.extlibrary.Library;
import org.eclipse.emf.examples.extlibrary.provider.EXTLibraryItemProviderAdapterFactory;
import org.eclipse.emf.examples.extlibrary.provider.LibraryItemProvider;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;

@Component
public class ExampleComponent {

	@Activate
	public void activate() {
		System.out.println(EXTLibraryPackage.eINSTANCE);
		Library l = EXTLibraryFactory.eINSTANCE.createLibrary();
		l.setName("Alexandria");
		
		XMIResource resource = new XMIResourceImpl();
		resource.getContents().add(l);
		try {
			resource.save(System.out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EXTLibraryItemProviderAdapterFactory());
		
		LibraryItemProvider libraryItemProvider = new LibraryItemProvider(null);
		System.out.println("ItemProvider.getText:" + libraryItemProvider.getText(l));
		AdapterFactoryItemDelegator delegator = new AdapterFactoryItemDelegator(adapterFactory);
		System.out.println("ItemDelegator.getText:" + delegator.getText(l));
		
		System.out.println("ItemDelegator.getText:" + delegator.getText(EcorePackage.eINSTANCE));
	}

}