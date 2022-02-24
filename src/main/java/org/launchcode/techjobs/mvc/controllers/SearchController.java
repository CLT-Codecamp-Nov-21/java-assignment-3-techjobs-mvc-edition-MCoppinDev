package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {



    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping(value="results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam(required = false)String searchTerm){

        ArrayList<Job> jobs;
        //Check if user selects all and puts nothing or all in the text input.
        if(searchType.equals("all") && searchTerm.equals("") || searchTerm.toLowerCase().equals("all")){
            //if so then jobs is all of the jobs in JobData using the findALl method.
            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");

        //if user selects all but inputs something in the text input.
        }else if(searchType.equals("all")){
            //utilize the find by value which will be the String searchTerm.
            jobs = JobData.findByValue(searchTerm);
            model.addAttribute("title", "Jobs with All: " + searchTerm);


        }else{
            //if the user selects something else and inputs text.
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);

        }

        model.addAttribute("jobs",jobs);
        model.addAttribute("columns", columnChoices);


        return "search";
    }


}
