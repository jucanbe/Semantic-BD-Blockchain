// SPDX-License-Identifier: GPL-3.0
pragma solidity 0.8.16 <0.9.0;

contract Energy_IoT {

    struct energy_comp{
        string ontology_dir;
        string identifier;
        string buildingName;
        string location;
        uint256 timestamp;
        uint256 energy;
    }

    event MyEvent(address ID, string ontology_dir, string indentifier, string buildingName, string location, uint256 timestamp, uint256 energy);

    mapping(address => energy_comp) public energyInfo;

    function storeDeviceStatus(address ID, string memory ontology_dir, string memory indentifier, string memory buildingName, string memory location, uint256 timestamp, uint256 energy) public {
        energyInfo[ID] = energy_comp(ontology_dir, indentifier, buildingName, location, timestamp, energy);
        emit MyEvent(ID, ontology_dir, indentifier, buildingName, location, timestamp, energy);
    }
}