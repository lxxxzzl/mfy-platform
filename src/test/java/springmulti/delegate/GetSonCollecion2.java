package springmulti.delegate;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class GetSonCollecion2 {

	private static Logger LOG = LoggerFactory.getLogger(GetSonCollecion2.class);
	
	public List<String> getSonCollecion() {
		LOG.info("-------------------GetSonCollecion---------------");
		return Arrays.asList("big son","mid son","small son");
	}
	
}
