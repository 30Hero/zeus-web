package vn.zeus.web.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {

	public static <D> D map(Object source, Class<D> destinationType) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(source, destinationType);
	}
}
