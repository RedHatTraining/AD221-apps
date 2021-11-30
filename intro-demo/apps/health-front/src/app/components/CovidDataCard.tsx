import React from 'react';
import {
	Card,
	CardTitle,
	CardBody,
	CardFooter,
	Grid,
	GridItem,
	DescriptionList,
	DescriptionListGroup,
	DescriptionListTerm,
	DescriptionListDescription
} from '@patternfly/react-core';
import { CovidDataEnriched } from '../models/CovidDataEnriched';


interface CovidDataProps {
	covidData: CovidDataEnriched
}


const CovidDataCard = (props: CovidDataProps): JSX.Element => {
	return (
		<Card id="card-demo-horizontal-split-example" isFlat>
			<Grid hasGutter sm={4} md={3} xl={2}>
				<GridItem >
					<CardTitle>Test</CardTitle>
					<CardBody>
						<DescriptionList>
							<DescriptionListGroup>
								<DescriptionListTerm>Country Name</DescriptionListTerm>
								<DescriptionListDescription>{props.covidData.countryName}</DescriptionListDescription>
							</DescriptionListGroup>
							<DescriptionListGroup>
								<DescriptionListTerm>Cumulative Deceased</DescriptionListTerm>
								<DescriptionListDescription>{props.covidData.cumulativeDeceased}</DescriptionListDescription>
							</DescriptionListGroup>
							<DescriptionListGroup>
								<DescriptionListTerm>First Dose</DescriptionListTerm>
								<DescriptionListDescription>{props.covidData.firstDose}</DescriptionListDescription>
							</DescriptionListGroup>
						</DescriptionList>
					</CardBody>
					<CardFooter></CardFooter>
				</GridItem>
			</Grid>
		</Card>
	);
}

export { CovidDataCard };

