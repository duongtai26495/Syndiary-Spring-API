package com.duongtai.estore.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duongtai.estore.configs.Snippets;
import com.duongtai.estore.entities.Vendor;
import com.duongtai.estore.repositories.VendorRepository;
import com.duongtai.estore.services.VendorService;
import static com.duongtai.estore.configs.MyUserDetail.getUsernameLogin;
@Service
public class VendorServiceImpl implements VendorService{
	private static final Logger LOG = LoggerFactory.getLogger(VendorServiceImpl.class);
	@Autowired
	private VendorRepository vendorRepository;
	
	@Override
	public Vendor saveVendor(Vendor vendor) {
			Date date = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);
	        vendor.setCreated_at(sdf.format(date));
	        vendor.setLast_edited(sdf.format(date));
	        vendor.setCreated_by(getUsernameLogin());
		LOG.info(String.format("Admin: '%s' have create new vendor name '%s' successfully",
				getUsernameLogin(),
				vendor.getName()));
			return vendorRepository.save(vendor);
	}

	@Override
	public Vendor editVendorById(Vendor vendor) {
		if(vendorRepository.existsById(vendor.getId())) {
			Vendor vendor_found = vendorRepository.findById(vendor.getId()).get();
			if(vendor.getImage() != null && !vendor.getImage().equals(vendor_found.getImage())) {
				vendor_found.setImage(vendor.getImage());
			}
			
			if(vendor.getName() != null && 
					!vendor.getName().toLowerCase().strip().equals(vendor_found.getName().toLowerCase().strip())) {
				vendor_found.setName(vendor.getName());
			}
			Date date = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);
			vendor_found.setLast_edited(sdf.format(date));
			return vendorRepository.save(vendor_found);
		}
		return saveVendor(vendor);
	}

	@Override
	public void deleteVendorById(Long id) {
			vendorRepository.deleteById(id);
	}

	@Override
	public List<Vendor> findAllVendor() {
		return vendorRepository.findAll();
	}

	@Override
	public boolean isExistByName(String name) {
		List<Vendor> vendors = vendorRepository.findAll();
		for(Vendor vendor_row : vendors) {
			if(vendor_row.getName().toLowerCase().strip()
				.equals(name.toLowerCase().strip())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Vendor findVendorById(Long id) {
		if(vendorRepository.existsById(id)) {
			return vendorRepository.findById(id).get();
		}
		return null;
	}
}
