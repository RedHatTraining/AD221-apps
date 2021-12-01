import React, { useState } from 'react';
import {
  Card,
  CardHeader,
  CardTitle,
  CardBody,
  CardExpandableContent,
  Level,
  Grid,
  Text,
  Badge,
  GridItem,
} from '@patternfly/react-core';
import {Caption, TableComposable, Tbody, Td, Th, Thead, Tr} from '@patternfly/react-table';
import { CovidDataEnriched } from '../models/CovidDataEnriched';
import { ChartDonut, ChartPie, ChartThemeColor } from '@patternfly/react-charts';


interface CovidDataProps {
	covidData: CovidDataEnriched
}

interface CardState {
	isCardExpanded: boolean;
}

const CovidDataCard = (props: CovidDataProps): JSX.Element => {

  const [state, setState] = useState<CardState>({
		isCardExpanded: false,
	});

	const onCardExpand = () => {
      setState({
        isCardExpanded: !state.isCardExpanded
      });
    };

	const flagImage = <img className="flag" src={props.covidData.flagImageURL}/>;

  const columns = ['Population', 'Cumulative Positive', 'Cumulative Deceased', 'Cumulative Recovered','Vaccine Type', 'First Dose', 'Second Dose'];
  const rows = [
    [props.covidData.population, props.covidData.cumulativePositive, props.covidData.cumulativeDeceased, props.covidData.cumulativeRecovered, 
      props.covidData.vaccine, props.covidData.firstDose, props.covidData.secondDose]
  ];

	return (
		<Card id="covidDataCard" isExpanded={state.isCardExpanded}>
        <CardHeader
          onExpand={onCardExpand}
          toggleButtonProps={{
            id: 'toggleButtonCovid',
            'aria-label': 'ActionsCovid',
            'aria-labelledby': 'cardTitleCovid toggleButtonCovid',
            'aria-expanded': state.isCardExpanded
          }}
        >
          {state.isCardExpanded && <CardTitle id="cardTitleCovid">{props.covidData.countryName} {flagImage}</CardTitle>}
          {!state.isCardExpanded && (
            <Level hasGutter>
              <CardTitle id="cardTitleCovid">{props.covidData.countryName} {flagImage}</CardTitle>
              {props.covidData.cumulativePositive != undefined && <Text>Cumulative Positive <Badge>{props.covidData.cumulativePositive}</Badge></Text>}
              {props.covidData.cumulativeDeceased != undefined && <Text>Cumulative Deceased <Badge className="case-dec">{props.covidData.cumulativeDeceased}</Badge></Text>}
              {props.covidData.firstDose != undefined && <Text>First Dose <Badge className="vacc-dose">{props.covidData.firstDose}</Badge></Text>}
              {props.covidData.secondDose != undefined && <Text>Second Dose <Badge className="vacc-dose">{props.covidData.secondDose}</Badge></Text>}
            </Level>
          )}
        </CardHeader>
        <CardExpandableContent>
          <CardBody>
            <Grid hasGutter>
              <GridItem span={10}>
      <TableComposable
        aria-label="Simple table"
      >
        <Caption>Covid-19 data of {props.covidData.countryName} for year week {props.covidData.yearWeekISO}. </Caption>
        <Thead>
          <Tr>
            {columns.map((column, columnIndex) => (
              <Th key={columnIndex}>{column}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {rows.map((row, rowIndex) => (
            <Tr key={rowIndex}>
              {row.map((cell, cellIndex) => (
                <Td key={`${rowIndex}_${cellIndex}`} dataLabel={columns[cellIndex]}>
                  {cell}
                </Td>
              ))}
            </Tr>
          ))}
        </Tbody>
      </TableComposable>
      </GridItem>   
      <GridItem span={5}>
      <div style={{ height: '230px', width: '400px' }}>
          <ChartPie
              ariaDesc="Covid Case Data"
              ariaTitle="Covid Case Data Chart"
              constrainToVisibleArea={true}
              data={[{ x: 'Cumulative Positive', y: props.covidData.cumulativePositive }, { x: 'Cumulative Deceased', y: props.covidData.cumulativeDeceased }, { x: 'Cumulative Recovered', y: props.covidData.cumulativeRecovered }]}
              height={230}
              labels={({ datum }) => `${datum.x}: ${datum.y}`}
              legendData={[{ name: 'C. Positive: ' + props.covidData.cumulativePositive }, { name: 'C. Deceased: '+ props.covidData.cumulativeDeceased }, { name: 'C. Recovered: '+props.covidData.cumulativeRecovered }]}
              legendPosition="right"
              legendOrientation="vertical"
              padding={{
                bottom: 20,
                left: 20,
                right: 145,
                top: 20
              }}
              themeColor={ChartThemeColor.orange}
              width={450}
          />
         </div>
       </GridItem>
        <GridItem span={5}>
        <div style={{ height: '230px', width: '400px' }}>
            <ChartDonut
                  ariaDesc="Covid Vacc. Data"
                  ariaTitle="Covid Vaccination Data Chart"
                  constrainToVisibleArea={true}
                  data={[{ x: 'First Dose', y: props.covidData.firstDose }, { x: 'Second Dose', y: props.covidData.secondDose }, { x: 'Unvaccinated', y: props.covidData.population - props.covidData.firstDose - props.covidData.secondDose }]}
                  height={230}
                  labels={({ datum }) => `${datum.x}: ${datum.y}`}
                  legendData={[{ name: 'First Dose: ' + props.covidData.firstDose }, { name: 'Second Dose: '+ props.covidData.secondDose }, { name: 'Unvaccinated: '+ (props.covidData.population - props.covidData.firstDose - props.covidData.secondDose) }]}
                  legendPosition="right"
                  legendOrientation="vertical"
                  padding={{
                    bottom: 20,
                    left: 20,
                    right: 145,
                    top: 20
                  }}
                  themeColor={ChartThemeColor.green}
                  width={450}
              />
          </div>
        </GridItem>
            </Grid>
          </CardBody>
        </CardExpandableContent>
      </Card>
	);
}

export { CovidDataCard };

