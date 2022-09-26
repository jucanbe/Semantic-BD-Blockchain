// SPDX-License-Identifier: GPL-3.0
pragma solidity 0.8.16 <0.9.0;

contract Sensor_IoT {

    struct temp_device{
        string ontology_dir;
        string identifier;
        string buildingName;
        string location;
        string office;
        uint256 timestamp;
        uint256 lux;
        uint256 co2;
        uint256 humidity;
        int256 temp;
    }

    event MyEvent(address ID, string ontology_dir, string indentifier, string buildingName, string location, string office, uint256 timestamp, uint256 lux, uint256 co2, uint256 humidity, int256 temp);

    mapping(address => temp_device) public deviceInfo;

    function storeDeviceStatus(address ID, string memory ontology_dir, string memory indentifier, string memory buildingName, string memory location, string memory office, uint256 timestamp, uint256 lux, uint256 co2, uint256 humidity, int256 temp) public {
        deviceInfo[ID] = temp_device(ontology_dir, indentifier, buildingName, location, office, timestamp, lux, co2, humidity, temp);
        emit MyEvent(ID, ontology_dir, indentifier, buildingName, location, office, timestamp, lux, co2, humidity, temp);
    }
}
