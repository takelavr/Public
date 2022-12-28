package com.rogovig.calcdistance.data;

import com.rogovig.calcdistance.model.Distance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface DistanceRepository extends CrudRepository<Distance,Long> {
}
